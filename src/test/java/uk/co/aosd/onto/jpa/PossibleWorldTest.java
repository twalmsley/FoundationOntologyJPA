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
import uk.co.aosd.onto.jpa.events.DeletedJpa;
import uk.co.aosd.onto.jpa.events.DestroyedJpa;

/**
 * Persistence tests for the PossibleWorldJpa class.
 */
public class PossibleWorldTest {
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
        final var from1 = new CreatedJpa("created001", Instant.now(), Instant.now());
        final var to1 = new DestroyedJpa("destroyed001", Instant.now(), Instant.now());
        final var from2 = new CreatedJpa("created002", Instant.now(), Instant.now());
        final var to2 = new DestroyedJpa("destroyed002", Instant.now(), Instant.now());
        final var item1 = new IndividualJpa("item001", from1, to1);
        final var item2 = new IndividualJpa("item002", from2, to2);
        final var beginning = new CreatedJpa("beginning001", Instant.now(), Instant.now());
        final var ending = new DeletedJpa("ending001", Instant.now(), Instant.now());
        final var entity = new PossibleWorldJpa("pw001", Set.of(item1, item2), beginning, ending);

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(PossibleWorldJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertTrue(entity.getParts().contains(item1));
        assertTrue(entity.getParts().contains(item2));
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getEnding(), found.getEnding());
    }

}
