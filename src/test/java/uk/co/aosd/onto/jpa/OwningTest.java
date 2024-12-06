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
import uk.co.aosd.onto.events.TransferredFrom;
import uk.co.aosd.onto.events.TransferredTo;
import uk.co.aosd.onto.jpa.events.CreatedJpa;
import uk.co.aosd.onto.jpa.events.DestroyedJpa;
import uk.co.aosd.onto.jpa.events.TransferredFromJpa;
import uk.co.aosd.onto.jpa.events.TransferredToJpa;

/**
 * Persistence test for OwningJpa.
 *
 * @author Tony Walmsley
 */
public class OwningTest {
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
        final var from1 = new CreatedJpa("created1", Instant.now(), Instant.now());
        final var to1 = new DestroyedJpa("destroyed1", Instant.now(), Instant.now());
        final var from2 = new CreatedJpa("created2", Instant.now(), Instant.now());
        final var to2 = new DestroyedJpa("destroyed2", Instant.now(), Instant.now());
        final var owner = new IndividualJpa("owner1", from1, to1);
        final var owned = new IndividualJpa("owned1", from2, to2);
        final TransferredFrom beginning = new TransferredFromJpa("beginning1", Instant.now(), Instant.now());
        final TransferredTo ending = new TransferredToJpa("ending1", Instant.now(), Instant.now());
        final var entity = new OwningJpa<>("owning1", "Owning Something", owner, owned, beginning, ending);

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(OwningJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getActionsDescription(), found.getActionsDescription());
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getOwner(), found.getOwner());
        assertEquals(entity.getOwned(), found.getOwned());
        assertEquals(entity.getEnding(), found.getEnding());
    }

}
