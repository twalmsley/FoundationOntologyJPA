package uk.co.aosd.onto.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Appointed;
import uk.co.aosd.onto.events.Birth;
import uk.co.aosd.onto.events.Death;
import uk.co.aosd.onto.events.Dissolved;
import uk.co.aosd.onto.events.Formed;
import uk.co.aosd.onto.events.Removed;
import uk.co.aosd.onto.events.Resignified;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.jpa.events.AppointedJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.DissolvedJpa;
import uk.co.aosd.onto.jpa.events.FormedJpa;
import uk.co.aosd.onto.jpa.events.RemovedJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.model.Model;
import uk.co.aosd.onto.organisation.Membership;
import uk.co.aosd.onto.organisation.Organisation;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * Test ModelJpa persistence.
 *
 * @author Tony Walmsley
 */
public class ModelTest {
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
        final Language english = new LanguageJpa("english", "English");
        final Birth birth = new BirthJpa("birth-1", Instant.now(), Instant.now());
        final Death death = new DeathJpa("death-1", Instant.now(), Instant.now());
        final Resignified personNameFrom = new ResignifiedJpa("person-name-from", Instant.now(), Instant.now());
        final Resignified personNameTo = new ResignifiedJpa("person-name-to", Instant.now(), Instant.now());
        final Signifier<String> personName1 = new SignifierJpa("person-name-1", "Person Name 1", english, personNameFrom, personNameTo);
        final Class<Signifier<String>> personNames = new ClassJpa<>("person-names-1", Set.of(personName1));
        final Class<Language> languages = new ClassJpa<>("languages-1", Set.of(english));
        final DNA dna = new DNAJpa("dna-1", "ATCG");
        final Human person = new HumanJpa("person1", birth, death, personNames, english, languages, dna);
        final Instant from = Instant.now();
        final Instant to = from.plus(10L, ChronoUnit.DAYS);
        final Appointed appointed = new AppointedJpa("appointed-1", from, from);
        final Removed removed = new RemovedJpa("removed-1", to, to);
        final EmployeeRole employeeRole = new EmployeeRole("employee-role-1", "Data Modeller", "Models Data");
        final Membership<EmployeeRole> membership1 = new MembershipJpa<>("membership-1", person, employeeRole, appointed, removed);
        final Class<Membership<EmployeeRole>> members = new ClassJpa<>("members-1", Set.of(membership1));
        final Class<Organisation> units = new ClassJpa<>("units-1", Set.of());
        final Formed beginning = new FormedJpa("formed-1", Instant.now(), Instant.now());
        final Dissolved ending = new DissolvedJpa("dissolved-1", Instant.now(), Instant.now());
        final Resignified namedFrom = new ResignifiedJpa("namedFrom", Instant.now(), Instant.now());
        final Resignified namedTo = new ResignifiedJpa("namedTo", Instant.now(), Instant.now());
        final Signifier<String> name1 = new SignifierJpa("name1", "Name 1", english, namedFrom, namedTo);
        final Class<Signifier<String>> names = new ClassJpa<>("names1", Set.of(name1));
        final Organisation org = new OrganisationJpa<EmployeeRole>("org-1", members, "To make money", units, names, beginning, ending);

        final var employment = new EmploymentJpa(
            "employment-1",
            org,
            person,
            "Data Modeller",
            "Work all day every day",
            appointed,
            removed);

        final Model entity = new ModelJpa("model-1", Set.of(employment));

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(ModelJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertTrue(entity.getThings().contains(employment));
    }
}
