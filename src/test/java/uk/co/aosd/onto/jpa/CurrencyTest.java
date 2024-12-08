package uk.co.aosd.onto.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for CurrencyJpa.
 *
 * @author Tony Walmsley
 */
public class CurrencyTest {
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
        final var entity = new CurrencyJpa(Util.randId(), "USD", "US Dollar", '$');

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(CurrencyJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getAbbreviation(), found.getAbbreviation());
        assertEquals(entity.getName(), found.getName());
        assertEquals(entity.getSymbol(), found.getSymbol());
    }
}
