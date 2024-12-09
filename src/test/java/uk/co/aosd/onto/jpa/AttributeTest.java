package uk.co.aosd.onto.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;

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
 * Test persistence for Attributes.
 *
 * @author Tony Walmsley
 */
public class AttributeTest {
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("postgres-persistence-unit");
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
    public void testAttribute() {
        final Instant to = Instant.now();
        final Instant from = to.minusSeconds(60);
        final var beginning = new BuiltJpa(Util.randId(), from, to);
        final var ending = new ScrappedJpa(Util.randId(), from, to);
        final var ind = new IndividualJpa<>(Util.randId(), beginning, ending);
        final var entity = new AttributeJpa<>(Util.randId(), ind, "prop", from, to);

        em.getTransaction().begin();
        em.persist(beginning);
        em.persist(ending);
        em.persist(ind);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(AttributeJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getIndividual(), found.getIndividual());
        assertEquals(entity.getProperty(), found.getProperty());
        assertEquals(entity.getFrom(), found.getFrom());
        assertEquals(entity.getTo(), found.getTo());
    }

    @Test
    public void testScalarAttribute() {
        final Instant to = Instant.now();
        final Instant from = to.minusSeconds(60);
        final var beginning = new BuiltJpa(Util.randId(), from, to);
        final var ending = new ScrappedJpa(Util.randId(), from, to);
        final var ind = new IndividualJpa<>(Util.randId(), beginning, ending);
        final var scalarValue = new ScalarValueJpa<>(1, Units.METERS);
        final var entity = new ScalarAttributeJpa<>(Util.randId(), ind, scalarValue, from, to);

        em.getTransaction().begin();
        em.persist(beginning);
        em.persist(ending);
        em.persist(ind);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(ScalarAttributeJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getIndividual(), found.getIndividual());
        assertEquals(entity.getProperty(), found.getProperty());
        assertEquals(entity.getFrom(), found.getFrom());
        assertEquals(entity.getTo(), found.getTo());
    }
}
