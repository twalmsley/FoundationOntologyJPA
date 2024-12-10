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
        emf = Persistence.createEntityManagerFactory("persistence-unit");
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
        final var ownerBorn = new BirthJpa(Util.randId(), Instant.now(), Instant.now());
        final var ownerDied = new DeathJpa(Util.randId(), Instant.now(), Instant.now());
        final var owner = new IndividualJpa<>(Util.randId(), ownerBorn, ownerDied);
        final var ownedCreated = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var ownedDestroyed = new DestroyedJpa(Util.randId(), Instant.now(), Instant.now());
        final var owned = new IndividualJpa<>(Util.randId(), ownedCreated, ownedDestroyed);

        final var own1From = new TransferredFromJpa(Util.randId(), Instant.now(), Instant.now());
        final var own1To = new TransferredToJpa(Util.randId(), Instant.now(), Instant.now());
        final var current = new OwningJpa<>(Util.randId(), "Owning", owner, owned, own1From, own1To);

        final var nextOwnerBorn = new BirthJpa(Util.randId(), Instant.now(), Instant.now());
        final var nextOwnerDied = new DeathJpa(Util.randId(), Instant.now(), Instant.now());
        final var nextOwner = new IndividualJpa<>(Util.randId(), nextOwnerBorn, nextOwnerDied);
        final var txferFrom = new StartedJpa(Util.randId(), Instant.now(), Instant.now());
        final var txFerTo = new StoppedJpa(Util.randId(), Instant.now(), Instant.now());
        final var entity = svc.transferOwnership(Util.randId(), "Transferring ownership", current, nextOwner, txferFrom, txFerTo);

        em.getTransaction().begin();
        em.persist(ownerBorn);
        em.persist(ownerDied);
        em.persist(owner);
        em.persist(ownedCreated);
        em.persist(ownedDestroyed);
        em.persist(owned);
        em.persist(own1From);
        em.persist(own1To);
        em.persist(current);
        em.persist(nextOwnerBorn);
        em.persist(nextOwnerDied);
        em.persist(nextOwner);
        em.persist(txferFrom);
        em.persist(txFerTo);
        em.persist(entity.getBeginning());
        em.persist(entity.getEnding());
        em.persist(entity.getTo());
        em.persist(entity.getTo().getBeginning());
        em.persist(entity.getTo().getEnding());
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
