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
import uk.co.aosd.onto.jpa.events.StartedJpa;
import uk.co.aosd.onto.jpa.events.StoppedJpa;
import uk.co.aosd.onto.jpa.events.TransferredFromJpa;
import uk.co.aosd.onto.jpa.events.TransferredToJpa;

/**
 * Test persistence of TransferringOfOwnershipJpa.
 *
 * @author Tony Walmsley
 */
public class TransferringOfOwnershipTest {
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
        final var svc = new OntologyServicesJpa();
        final var ownerBorn = new BirthJpa("ownerBorn", Instant.now(), Instant.now());
        final var ownerDied = new DeathJpa("ownerDied", Instant.now(), Instant.now());
        final var owner = new IndividualJpa("owner", ownerBorn, ownerDied);
        final var ownedCreated = new CreatedJpa("ownedCreated", Instant.now(), Instant.now());
        final var ownedDestroyed = new DestroyedJpa("ownedDestroyed", Instant.now(), Instant.now());
        final var owned = new IndividualJpa("owned", ownedCreated, ownedDestroyed);

        final var own1From = new TransferredFromJpa("own1From", Instant.now(), Instant.now());
        final var own1To = new TransferredToJpa("own1To", Instant.now(), Instant.now());
        final var current = new OwningJpa<>("own1", "Owning", owner, owned, own1From, own1To);

        final var nextOwnerBorn = new BirthJpa("nextOwnerBown", Instant.now(), Instant.now());
        final var nextOwnerDied = new DeathJpa("nextOwnerDied", Instant.now(), Instant.now());
        final var nextOwner = new IndividualJpa("newOwner", nextOwnerBorn, nextOwnerDied);
        final var txferFrom = new StartedJpa("txferFrom", Instant.now(), Instant.now());
        final var txFerTo = new StoppedJpa("txferTo", Instant.now(), Instant.now());
        final var entity = svc.transferOwnership("txfer", "Transferring ownership", current, nextOwner, txferFrom, txFerTo);

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(TransferringOfOwnershipJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getFrom().getOwner(), owner);
        assertEquals(entity.getFrom().getOwned(), owned);
        assertEquals(entity.getTo().getOwned(), owned);
        assertEquals(entity.getTo().getOwner(), nextOwner);
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getEnding(), found.getEnding());
    }
}
