package uk.co.aosd.onto.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.aosd.onto.events.Aggregated;
import uk.co.aosd.onto.events.Disaggregated;
import uk.co.aosd.onto.jpa.events.AggregatedJpa;
import uk.co.aosd.onto.jpa.events.BuiltJpa;
import uk.co.aosd.onto.jpa.events.CreatedJpa;
import uk.co.aosd.onto.jpa.events.DestroyedJpa;
import uk.co.aosd.onto.jpa.events.DisaggregatedJpa;
import uk.co.aosd.onto.jpa.events.EventJpa;
import uk.co.aosd.onto.jpa.events.ScrappedJpa;

/**
 * Test persistence for Agglomerates.
 *
 * @author Tony Walmsley
 */
public class AgglomerateTest {
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
        final EventJpa handleCreated = new CreatedJpa(Util.randId(), null, null);
        final EventJpa handleDestroyed = new DestroyedJpa(Util.randId(), null, null);
        final EventJpa headBuilt = new BuiltJpa(Util.randId(), null, null);
        final EventJpa headScrapped = new ScrappedJpa(Util.randId(), null, null);
        final Aggregated beginning = new AggregatedJpa(Util.randId(), null, null);
        final Disaggregated ending = new DisaggregatedJpa(Util.randId(), null, null);

        final IndividualJpa handle = new IndividualJpa(Util.randId(), handleCreated, handleDestroyed);
        final IndividualJpa head = new IndividualJpa(Util.randId(), headBuilt, headScrapped);
        final Set<IndividualJpa> parts = Set.of(handle, head);
        final var entity = new AgglomerateJpa(Util.randId(), parts, beginning, ending);
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(AgglomerateJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getParts(), found.getParts());
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getEnding(), found.getEnding());
    }
}
