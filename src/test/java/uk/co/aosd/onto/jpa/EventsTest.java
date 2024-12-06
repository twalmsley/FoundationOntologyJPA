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
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.jpa.events.AggregatedJpa;
import uk.co.aosd.onto.jpa.events.AppointedJpa;
import uk.co.aosd.onto.jpa.events.AssembledJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.BoughtJpa;
import uk.co.aosd.onto.jpa.events.BuiltJpa;
import uk.co.aosd.onto.jpa.events.ChangedJpa;
import uk.co.aosd.onto.jpa.events.CreatedJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.DecommissionedJpa;
import uk.co.aosd.onto.jpa.events.DeletedJpa;
import uk.co.aosd.onto.jpa.events.DestroyedJpa;
import uk.co.aosd.onto.jpa.events.DisaggregatedJpa;
import uk.co.aosd.onto.jpa.events.DisassembledJpa;
import uk.co.aosd.onto.jpa.events.DissolvedJpa;
import uk.co.aosd.onto.jpa.events.EventJpa;
import uk.co.aosd.onto.jpa.events.FormedJpa;
import uk.co.aosd.onto.jpa.events.InstalledJpa;
import uk.co.aosd.onto.jpa.events.RemovedJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.jpa.events.ScrappedJpa;
import uk.co.aosd.onto.jpa.events.SoldJpa;
import uk.co.aosd.onto.jpa.events.StartedJpa;
import uk.co.aosd.onto.jpa.events.StoppedJpa;
import uk.co.aosd.onto.jpa.events.TransferredFromJpa;
import uk.co.aosd.onto.jpa.events.TransferredToJpa;

/**
 * Test persistence for Events subtypes.
 *
 * @author Tony Walmsley
 */
public class EventsTest {
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
        final EventJpa e1 = new AggregatedJpa("Event1", Instant.ofEpochSecond(1), Instant.ofEpochSecond(2));
        final EventJpa e2 = new AppointedJpa("Event2", Instant.ofEpochSecond(2), Instant.ofEpochSecond(3));
        final EventJpa e3 = new AssembledJpa("Event3", Instant.ofEpochSecond(3), Instant.ofEpochSecond(4));
        final EventJpa e4 = new BirthJpa("Event4", Instant.ofEpochSecond(4), Instant.ofEpochSecond(5));
        final EventJpa e5 = new BoughtJpa("Event5", Instant.ofEpochSecond(5), Instant.ofEpochSecond(6));
        final EventJpa e6 = new BuiltJpa("Event6", Instant.ofEpochSecond(6), Instant.ofEpochSecond(7));
        final EventJpa e7 = new ChangedJpa("Event7", Instant.ofEpochSecond(7), Instant.ofEpochSecond(8));
        final EventJpa e8 = new CreatedJpa("Event8", Instant.ofEpochSecond(7), Instant.ofEpochSecond(8));
        final EventJpa e9 = new DeathJpa("Event9", Instant.ofEpochSecond(8), Instant.ofEpochSecond(9));
        final EventJpa e10 = new DecommissionedJpa("Event10", Instant.ofEpochSecond(9), Instant.ofEpochSecond(10));
        final EventJpa e11 = new DeletedJpa("Event11", Instant.ofEpochSecond(10), Instant.ofEpochSecond(11));
        final EventJpa e12 = new DestroyedJpa("Event12", Instant.ofEpochSecond(11), Instant.ofEpochSecond(12));
        final EventJpa e13 = new DisaggregatedJpa("Event13", Instant.ofEpochSecond(12), Instant.ofEpochSecond(13));
        final EventJpa e14 = new DisassembledJpa("Event14", Instant.ofEpochSecond(13), Instant.ofEpochSecond(14));
        final EventJpa e15 = new DissolvedJpa("Event15", Instant.ofEpochSecond(14), Instant.ofEpochSecond(15));
        final EventJpa e16 = new FormedJpa("Event16", Instant.ofEpochSecond(15), Instant.ofEpochSecond(16));
        final EventJpa e17 = new InstalledJpa("Event17", Instant.ofEpochSecond(16), Instant.ofEpochSecond(17));
        final EventJpa e18 = new RemovedJpa("Event18", Instant.ofEpochSecond(17), Instant.ofEpochSecond(18));
        final EventJpa e19 = new ResignifiedJpa("Event19", Instant.ofEpochSecond(18), Instant.ofEpochSecond(19));
        final EventJpa e20 = new ScrappedJpa("Event20", Instant.ofEpochSecond(19), Instant.ofEpochSecond(20));
        final EventJpa e21 = new SoldJpa("Event21", Instant.ofEpochSecond(20), Instant.ofEpochSecond(21));
        final EventJpa e22 = new StartedJpa("Event22", Instant.ofEpochSecond(21), Instant.ofEpochSecond(22));
        final EventJpa e23 = new StoppedJpa("Event23", Instant.ofEpochSecond(22), Instant.ofEpochSecond(23));
        final EventJpa e24 = new TransferredFromJpa("Event24", Instant.ofEpochSecond(23), Instant.ofEpochSecond(24));
        final EventJpa e25 = new TransferredToJpa("Event25", Instant.ofEpochSecond(24), Instant.ofEpochSecond(25));
        check(e1, AggregatedJpa.class);
        check(e2, AppointedJpa.class);
        check(e3, AssembledJpa.class);
        check(e4, BirthJpa.class);
        check(e5, BoughtJpa.class);
        check(e6, BuiltJpa.class);
        check(e7, ChangedJpa.class);
        check(e8, CreatedJpa.class);
        check(e9, DeathJpa.class);
        check(e10, DecommissionedJpa.class);
        check(e11, DeletedJpa.class);
        check(e12, DestroyedJpa.class);
        check(e13, DisaggregatedJpa.class);
        check(e14, DisassembledJpa.class);
        check(e15, DissolvedJpa.class);
        check(e16, FormedJpa.class);
        check(e17, InstalledJpa.class);
        check(e18, RemovedJpa.class);
        check(e19, ResignifiedJpa.class);
        check(e20, ScrappedJpa.class);
        check(e21, SoldJpa.class);
        check(e22, StartedJpa.class);
        check(e23, StoppedJpa.class);
        check(e24, TransferredFromJpa.class);
        check(e25, TransferredToJpa.class);
    }

    private void check(final Event entity, final Class<? extends Event> c) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(c, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getFrom(), found.getFrom());
        assertEquals(entity.getTo(), found.getTo());
    }
}
