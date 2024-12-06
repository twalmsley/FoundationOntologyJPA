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
import uk.co.aosd.onto.jpa.events.CreatedJpa;
import uk.co.aosd.onto.jpa.events.DestroyedJpa;

/**
 * Persistence tests for the PropertyJpa class.
 *
 * @author Tony Walmsley
 */
public class PropertyTest {
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
        final var from1 = new CreatedJpa("created011", Instant.now(), Instant.now());
        final var to1 = new DestroyedJpa("destroyed011", Instant.now(), Instant.now());
        final var from2 = new CreatedJpa("created012", Instant.now(), Instant.now());
        final var to2 = new DestroyedJpa("destroyed012", Instant.now(), Instant.now());
        final var item1 = new IndividualJpa("item011", from1, to1);
        final var item2 = new IndividualJpa("item012", from2, to2);
        final var entity = new PropertyJpa<>("pw011", Set.of(item1, item2), "Has this property");

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(PropertyJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertTrue(entity.getMembers().contains(item1));
        assertTrue(entity.getMembers().contains(item2));
        assertEquals(entity.getProperty(), "Has this property");
    }

}
