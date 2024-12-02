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

/**
 * Test persistence for Attributes.
 */
public class AttributeTest {
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
        final var beginning = new BuiltJpa("built1", from, to);
        final var ending = new ScrappedJpa("scrapped", from, to);
        final var ind = new IndividualJpa("ind", beginning, ending);
        final var entity = new AttributeJpa<>("id", ind, "prop", from, to);

        em.getTransaction().begin();
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
}
