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
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.CreatedJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.DestroyedJpa;

/**
 * Test persistence of StateJpa.
 *
 * @author Tony Walmsley
 */
public class StateTest {
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
        final var personFrom = new BirthJpa(Util.randId(), Instant.now(), Instant.now());
        final var personTo = new DeathJpa(Util.randId(), Instant.now(), Instant.now());
        final var person = new IndividualJpa(Util.randId(), personFrom, personTo);
        final var stateFrom = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var stateTo = new DestroyedJpa(Util.randId(), Instant.now(), Instant.now());
        final var entity = new StateJpa<>(Util.randId(), person, stateFrom, stateTo);

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(StateJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getIndividual(), found.getIndividual());
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getEnding(), found.getEnding());
    }
}
