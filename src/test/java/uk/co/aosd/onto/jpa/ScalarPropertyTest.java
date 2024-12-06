package uk.co.aosd.onto.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.aosd.onto.jpa.events.BuiltJpa;
import uk.co.aosd.onto.jpa.events.ScrappedJpa;
import uk.co.aosd.onto.units.Units;

/**
 * Test persistence for ScalarPropertyJpa.
 *
 * @author Tony Walmsley
 */
public class ScalarPropertyTest {
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("h2-persistence-unit");
    }

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
    }

    /**
     * Tear down.
     */
    @AfterEach
    public void tearDown() {
        if (em != null) {
            em.close();
        }
    }

    /**
     * Tear down.
     */
    @AfterAll
    public static void tearDownClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void test() {
        final Instant to = Instant.now();
        final Instant from = to.minusSeconds(60);
        final var beginning = new BuiltJpa(Util.randId(), from, to);
        final var ending = new ScrappedJpa(Util.randId(), from, to);
        final var ind = new IndividualJpa(Util.randId(), beginning, ending);
        final var scalarValue = new ScalarValueJpa<>(1, Units.METERS);
        final var entity = new ScalarPropertyJpa<>(Util.randId(), scalarValue, Set.of(ind));

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(ScalarPropertyJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertTrue(entity.getMembers().contains(ind));
        assertEquals(entity.getProperty(), found.getProperty());
    }
}
