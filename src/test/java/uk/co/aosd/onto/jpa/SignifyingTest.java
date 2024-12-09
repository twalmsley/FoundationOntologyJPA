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
import uk.co.aosd.onto.events.Resignified;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.language.Language;

/**
 * Test the SignifyingJpa class.
 *
 * @author Tony Walmsley
 */
public class SignifyingTest {
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("postgres-persistence-unit");
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
        final Language english = new LanguageJpa(Util.randId(), "English");
        final Resignified personNameFrom = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified personNameTo = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final UniquelyIdentifiable person = new UniquelyIdentifiableJpa(Util.randId());
        final SignifyingJpa entity = new SignifyingJpa(Util.randId(), "The act of signifying something", "Alice", english, person, personNameFrom,
            personNameTo);

        em.getTransaction().begin();
        em.persist(english);
        em.persist(personNameFrom);
        em.persist(personNameTo);
        em.persist(person);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(SignifyingJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getLanguage(), found.getLanguage());
        assertEquals(entity.getName(), found.getName());
        assertEquals(entity.getNamed(), found.getNamed());
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getEnding(), found.getEnding());
    }
}
