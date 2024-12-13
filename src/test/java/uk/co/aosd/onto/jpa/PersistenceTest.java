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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Aggregated;
import uk.co.aosd.onto.events.Appointed;
import uk.co.aosd.onto.events.Birth;
import uk.co.aosd.onto.events.Death;
import uk.co.aosd.onto.events.Disaggregated;
import uk.co.aosd.onto.events.Dissolved;
import uk.co.aosd.onto.events.Formed;
import uk.co.aosd.onto.events.Removed;
import uk.co.aosd.onto.events.Resignified;
import uk.co.aosd.onto.events.TransferredFrom;
import uk.co.aosd.onto.events.TransferredTo;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
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
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.model.Model;
import uk.co.aosd.onto.organisation.Membership;
import uk.co.aosd.onto.organisation.Organisation;
import uk.co.aosd.onto.signifying.Signifier;
import uk.co.aosd.onto.units.Units;

/**
 * Test persistence for Agglomerates.
 *
 * @author Tony Walmsley
 */
public class PersistenceTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("persistence-unit");
        em = emf.createEntityManager();
    }

    /**
     * Tear down.
     */
    @AfterAll
    public static void tearDownClass() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void testAgglomerate() {
        final EventJpa handleCreated = new CreatedJpa(Util.randId(), null, null);
        final EventJpa handleDestroyed = new DestroyedJpa(Util.randId(), null, null);
        final EventJpa headBuilt = new BuiltJpa(Util.randId(), null, null);
        final EventJpa headScrapped = new ScrappedJpa(Util.randId(), null, null);
        final Aggregated beginning = new AggregatedJpa(Util.randId(), null, null);
        final Disaggregated ending = new DisaggregatedJpa(Util.randId(), null, null);

        final var handle = new IndividualJpa<>(Util.randId(), handleCreated, handleDestroyed);
        final var head = new IndividualJpa<>(Util.randId(), headBuilt, headScrapped);
        final var parts = Set.of(handle, head);
        final var entity = new AgglomerateJpa(Util.randId(), parts, beginning, ending);
        em.getTransaction().begin();
        em.persist(handleCreated);
        em.persist(handleDestroyed);
        em.persist(headBuilt);
        em.persist(headScrapped);
        em.persist(beginning);
        em.persist(ending);
        em.persist(handle);
        em.persist(head);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(AgglomerateJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getParts(), found.getParts());
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getEnding(), found.getEnding());

        final var found2 = em.find(UniquelyIdentifiableJpa.class, entity.getIdentifier());
        assertNotNull(found2);
        assertEquals(entity.getIdentifier(), found2.getIdentifier());
    }

    @Test
    public void testAggregate() {
        final Instant aggTo = Instant.now();
        final Instant aggFrom = aggTo.minusSeconds(60);
        final var entity = new AggregateJpa<>(
            Util.randId(),
            Sand.class,
            new ScalarValueJpa<>(2, Units.KILOGRAMS),
            new AggregatedJpa(Util.randId(), aggFrom, aggFrom),
            new DisaggregatedJpa(Util.randId(), aggTo, aggTo));

        em.getTransaction().begin();
        em.persist(entity.getBeginning());
        em.persist(entity.getEnding());
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(AggregateJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getQuantity(), found.getQuantity());
        assertEquals(entity.getKind(), found.getKind());
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getEnding(), found.getEnding());
    }

    private static class Sand {
    }

    @Test
    public void testAttribute() {
        final Instant to = Instant.now();
        final Instant from = to.minusSeconds(60);
        final var beginning = new BuiltJpa(Util.randId(), from, to);
        final var ending = new ScrappedJpa(Util.randId(), from, to);
        final var ind = new IndividualJpa<>(Util.randId(), beginning, ending);
        final var entity = new AttributeJpa<>(Util.randId(), ind, "prop", from, to);

        em.getTransaction().begin();
        em.persist(beginning);
        em.persist(ending);
        em.persist(ind);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(AttributeJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getIndividual(), found.getIndividual());
        assertEquals(entity.getProperty(), found.getProperty());
        assertEquals(entity.getFrom(), found.getFrom());
        assertEquals(entity.getTo(), found.getTo());
    }

    @Test
    public void testScalarAttribute() {
        final Instant to = Instant.now();
        final Instant from = to.minusSeconds(60);
        final var beginning = new BuiltJpa(Util.randId(), from, to);
        final var ending = new ScrappedJpa(Util.randId(), from, to);
        final var ind = new IndividualJpa<>(Util.randId(), beginning, ending);
        final var scalarValue = new ScalarValueJpa<>(1, Units.METERS);
        final var entity = new ScalarAttributeJpa<>(Util.randId(), ind, scalarValue, from, to);

        em.getTransaction().begin();
        em.persist(beginning);
        em.persist(ending);
        em.persist(ind);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(ScalarAttributeJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getIndividual(), found.getIndividual());
        assertEquals(entity.getProperty(), found.getProperty());
        assertEquals(entity.getFrom(), found.getFrom());
        assertEquals(entity.getTo(), found.getTo());
    }

    @Test
    public void testClasses() {

        final var beginning = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var ending = new DeletedJpa(Util.randId(), Instant.now(), Instant.now());
        final var e1 = new IndividualJpa<>(Util.randId(), beginning, ending);
        final var e2 = new IndividualJpa<>(Util.randId(), beginning, ending);
        final var entity = new ClassJpa<UniquelyIdentifiable>(Util.randId(), Set.of(e1, e2));

        em.getTransaction().begin();
        em.persist(beginning);
        em.persist(ending);
        em.persist(e2);
        em.persist(e1);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(ClassJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertTrue(entity.getMembers().contains(e1));
        assertTrue(entity.getMembers().contains(e2));

        // Save another class with the same members
        final var entity2 = new ClassJpa<UniquelyIdentifiable>(Util.randId(), Set.of(e1, e2));
        em.getTransaction().begin();
        em.persist(entity2);
        em.getTransaction().commit();

        // Check that the members are the same
        final var found2 = em.find(ClassJpa.class, entity2.getIdentifier());
        assertNotNull(found2);
        assertEquals(entity2.getIdentifier(), found2.getIdentifier());
        assertTrue(entity2.getMembers().contains(e1));
        assertTrue(entity2.getMembers().contains(e2));
    }

    @Test
    public void testCurrency() {
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

    @Test
    public void testDna() {
        final var entity = new DNAJpa(Util.randId(), "gattaca");

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(DNAJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getDna(), found.getDna());
    }

    @Test
    public void testEmployment() {
        final Language english = new LanguageJpa(Util.randId(), "English");
        final Birth birth = new BirthJpa(Util.randId(), Instant.now(), Instant.now());
        final Death death = new DeathJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified personNameFrom = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified personNameTo = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Signifier<String> personName1 = new SignifierJpa(Util.randId(), "Person Name 1", english, personNameFrom, personNameTo);
        final Class<Signifier<String>> personNames = new ClassJpa<>(Util.randId(), Set.of(personName1));
        final Class<Language> languages = new ClassJpa<>(Util.randId(), Set.of(english));
        final DNA dna = new DNAJpa(Util.randId(), "ATCG");
        final Human person = new HumanJpa(Util.randId(), birth, death, personNames, english, languages, dna);
        final Instant from = Instant.now();
        final Instant to = from.plus(10L, ChronoUnit.DAYS);
        final Appointed appointed = new AppointedJpa(Util.randId(), from, from);
        final Removed removed = new RemovedJpa(Util.randId(), to, to);
        final EmployeeRole employeeRole = new EmployeeRole(Util.randId(), "Data Modeller", "Models Data");
        final Membership<EmployeeRole> membership1 = new MembershipJpa<>(Util.randId(), person, employeeRole, appointed, removed);
        final Class<Membership<EmployeeRole>> members = new ClassJpa<>(Util.randId(), Set.of(membership1));
        final Class<Organisation> units = new ClassJpa<>(Util.randId(), Set.of());
        final Formed beginning = new FormedJpa(Util.randId(), Instant.now(), Instant.now());
        final Dissolved ending = new DissolvedJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified namedFrom = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified namedTo = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Signifier<String> name1 = new SignifierJpa(Util.randId(), "Name 1", english, namedFrom, namedTo);
        final Class<Signifier<String>> names = new ClassJpa<>(Util.randId(), Set.of(name1));
        final Organisation org = new OrganisationJpa<EmployeeRole>(Util.randId(), members, "To make money", units, names, beginning, ending);

        final var entity = new EmploymentJpa(
            Util.randId(),
            org,
            person,
            "Data Modeller",
            "Work all day every day",
            appointed,
            removed);

        em.getTransaction().begin();
        em.persist(english);
        em.persist(birth);
        em.persist(death);
        em.persist(personNameFrom);
        em.persist(personNameTo);
        em.persist(personName1);
        em.persist(personNames);
        em.persist(languages);
        em.persist(dna);
        em.persist(person);
        em.persist(appointed);
        em.persist(removed);
        em.persist(employeeRole);
        em.persist(membership1);
        em.persist(members);
        em.persist(units);
        em.persist(beginning);
        em.persist(ending);
        em.persist(namedFrom);
        em.persist(namedTo);
        em.persist(name1);
        em.persist(names);
        em.persist(org);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(EmploymentJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getActionsDescription(), found.getActionsDescription());
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getContract(), found.getContract());
        assertEquals(entity.getEmployee(), found.getEmployee());
        assertEquals(entity.getEmployer(), found.getEmployer());
        assertEquals(entity.getEnding(), found.getEnding());
    }

    @Test
    public void testEvents() {
        final var e1 = new AggregatedJpa(Util.randId(), Instant.ofEpochSecond(1), Instant.ofEpochSecond(2));
        final var e2 = new AppointedJpa(Util.randId(), Instant.ofEpochSecond(2), Instant.ofEpochSecond(3));
        final var e3 = new AssembledJpa(Util.randId(), Instant.ofEpochSecond(3), Instant.ofEpochSecond(4));
        final var e4 = new BirthJpa(Util.randId(), Instant.ofEpochSecond(4), Instant.ofEpochSecond(5));
        final var e5 = new BoughtJpa(Util.randId(), Instant.ofEpochSecond(5), Instant.ofEpochSecond(6));
        final var e6 = new BuiltJpa(Util.randId(), Instant.ofEpochSecond(6), Instant.ofEpochSecond(7));
        final var e7 = new ChangedJpa(Util.randId(), Instant.ofEpochSecond(7), Instant.ofEpochSecond(8));
        final var e8 = new CreatedJpa(Util.randId(), Instant.ofEpochSecond(7), Instant.ofEpochSecond(8));
        final var e9 = new DeathJpa(Util.randId(), Instant.ofEpochSecond(8), Instant.ofEpochSecond(9));
        final var e10 = new DecommissionedJpa(Util.randId(), Instant.ofEpochSecond(9), Instant.ofEpochSecond(10));
        final var e11 = new DeletedJpa(Util.randId(), Instant.ofEpochSecond(10), Instant.ofEpochSecond(11));
        final var e12 = new DestroyedJpa(Util.randId(), Instant.ofEpochSecond(11), Instant.ofEpochSecond(12));
        final var e13 = new DisaggregatedJpa(Util.randId(), Instant.ofEpochSecond(12), Instant.ofEpochSecond(13));
        final var e14 = new DisassembledJpa(Util.randId(), Instant.ofEpochSecond(13), Instant.ofEpochSecond(14));
        final var e15 = new DissolvedJpa(Util.randId(), Instant.ofEpochSecond(14), Instant.ofEpochSecond(15));
        final var e16 = new FormedJpa(Util.randId(), Instant.ofEpochSecond(15), Instant.ofEpochSecond(16));
        final var e17 = new InstalledJpa(Util.randId(), Instant.ofEpochSecond(16), Instant.ofEpochSecond(17));
        final var e18 = new RemovedJpa(Util.randId(), Instant.ofEpochSecond(17), Instant.ofEpochSecond(18));
        final var e19 = new ResignifiedJpa(Util.randId(), Instant.ofEpochSecond(18), Instant.ofEpochSecond(19));
        final var e20 = new ScrappedJpa(Util.randId(), Instant.ofEpochSecond(19), Instant.ofEpochSecond(20));
        final var e21 = new SoldJpa(Util.randId(), Instant.ofEpochSecond(20), Instant.ofEpochSecond(21));
        final var e22 = new StartedJpa(Util.randId(), Instant.ofEpochSecond(21), Instant.ofEpochSecond(22));
        final var e23 = new StoppedJpa(Util.randId(), Instant.ofEpochSecond(22), Instant.ofEpochSecond(23));
        final var e24 = new TransferredFromJpa(Util.randId(), Instant.ofEpochSecond(23), Instant.ofEpochSecond(24));
        final var e25 = new TransferredToJpa(Util.randId(), Instant.ofEpochSecond(24), Instant.ofEpochSecond(25));
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

    private static void check(final Event entity, final java.lang.Class<? extends Event> c) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(c, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getFrom(), found.getFrom());
        assertEquals(entity.getTo(), found.getTo());
    }

    @Test
    public void testModel() {
        final Language english = new LanguageJpa(Util.randId(), "English");
        final Birth birth = new BirthJpa(Util.randId(), Instant.now(), Instant.now());
        final Death death = new DeathJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified personNameFrom = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified personNameTo = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Signifier<String> personName1 = new SignifierJpa(Util.randId(), "Person Name 1", english, personNameFrom, personNameTo);
        final Class<Signifier<String>> personNames = new ClassJpa<>(Util.randId(), Set.of(personName1));
        final Class<Language> languages = new ClassJpa<>(Util.randId(), Set.of(english));
        final DNA dna = new DNAJpa(Util.randId(), "ATCG");
        final Human person = new HumanJpa(Util.randId(), birth, death, personNames, english, languages, dna);
        final Instant from = Instant.now();
        final Instant to = from.plus(10L, ChronoUnit.DAYS);
        final Appointed appointed = new AppointedJpa(Util.randId(), from, from);
        final Removed removed = new RemovedJpa(Util.randId(), to, to);
        final EmployeeRole employeeRole = new EmployeeRole(Util.randId(), "Data Modeller", "Models Data");
        final Membership<EmployeeRole> membership1 = new MembershipJpa<>(Util.randId(), person, employeeRole, appointed, removed);
        final Class<Membership<EmployeeRole>> members = new ClassJpa<>(Util.randId(), Set.of(membership1));
        final Class<Organisation> units = new ClassJpa<>(Util.randId(), Set.of());
        final Formed beginning = new FormedJpa(Util.randId(), Instant.now(), Instant.now());
        final Dissolved ending = new DissolvedJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified namedFrom = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified namedTo = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Signifier<String> name1 = new SignifierJpa(Util.randId(), "Name 1", english, namedFrom, namedTo);
        final Class<Signifier<String>> names = new ClassJpa<>(Util.randId(), Set.of(name1));
        final Organisation org = new OrganisationJpa<EmployeeRole>(Util.randId(), members, "To make money", units, names, beginning, ending);

        final var employment = new EmploymentJpa(
            Util.randId(),
            org,
            person,
            "Data Modeller",
            "Work all day every day",
            appointed,
            removed);

        final Model entity = new ModelJpa(Util.randId(), Set.of(employment));

        em.getTransaction().begin();
        em.persist(english);
        em.persist(birth);
        em.persist(death);
        em.persist(personNameFrom);
        em.persist(personNameTo);
        em.persist(personName1);
        em.persist(personNames);
        em.persist(languages);
        em.persist(dna);
        em.persist(person);
        em.persist(appointed);
        em.persist(removed);
        em.persist(employeeRole);
        em.persist(membership1);
        em.persist(members);
        em.persist(units);
        em.persist(beginning);
        em.persist(ending);
        em.persist(namedFrom);
        em.persist(namedTo);
        em.persist(name1);
        em.persist(names);
        em.persist(org);
        em.persist(employment);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(ModelJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertTrue(entity.getThings().contains(employment));
    }

    @Test
    public void testOwning() {
        final var from1 = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var to1 = new DestroyedJpa(Util.randId(), Instant.now(), Instant.now());
        final var from2 = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var to2 = new DestroyedJpa(Util.randId(), Instant.now(), Instant.now());
        final var owner = new IndividualJpa<>(Util.randId(), from1, to1);
        final var owned = new IndividualJpa<>(Util.randId(), from2, to2);
        final TransferredFrom beginning = new TransferredFromJpa(Util.randId(), Instant.now(), Instant.now());
        final TransferredTo ending = new TransferredToJpa(Util.randId(), Instant.now(), Instant.now());
        final var entity = new OwningJpa<>(Util.randId(), "Owning Something", owner, owned, beginning, ending);

        em.getTransaction().begin();
        em.persist(from1);
        em.persist(to1);
        em.persist(from2);
        em.persist(to2);
        em.persist(owner);
        em.persist(owned);
        em.persist(beginning);
        em.persist(ending);
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

    @Test
    public void testPossibleWorld() {
        final var from1 = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var to1 = new DestroyedJpa(Util.randId(), Instant.now(), Instant.now());
        final var from2 = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var to2 = new DestroyedJpa(Util.randId(), Instant.now(), Instant.now());
        final var item1 = new IndividualJpa<>(Util.randId(), from1, to1);
        final var item2 = new IndividualJpa<>(Util.randId(), from2, to2);
        final var beginning = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var ending = new DeletedJpa(Util.randId(), Instant.now(), Instant.now());
        final var entity = new PossibleWorldJpa(Util.randId(), Set.of(item1, item2), beginning, ending);

        em.getTransaction().begin();
        em.persist(from1);
        em.persist(to1);
        em.persist(from2);
        em.persist(to2);
        em.persist(beginning);
        em.persist(ending);
        em.persist(item1);
        em.persist(item2);
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

    @Test
    public void testProperty() {
        final var from1 = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var to1 = new DestroyedJpa(Util.randId(), Instant.now(), Instant.now());
        final var from2 = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var to2 = new DestroyedJpa(Util.randId(), Instant.now(), Instant.now());
        final var item1 = new IndividualJpa<>(Util.randId(), from1, to1);
        final var item2 = new IndividualJpa<>(Util.randId(), from2, to2);
        final var entity = new PropertyJpa<>(Util.randId(), Set.of(item1, item2), "Has this property");

        em.getTransaction().begin();
        em.persist(from1);
        em.persist(to1);
        em.persist(from2);
        em.persist(to2);
        em.persist(item1);
        em.persist(item2);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(PropertyJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertTrue(entity.getMembers().contains(item1));
        assertTrue(entity.getMembers().contains(item2));
        assertEquals(entity.getProperty(), "Has this property");
    }

    @Test
    public void testScalarProperty() {
        final Instant to = Instant.now();
        final Instant from = to.minusSeconds(60);
        final var beginning = new BuiltJpa(Util.randId(), from, to);
        final var ending = new ScrappedJpa(Util.randId(), from, to);
        final var ind = new IndividualJpa<>(Util.randId(), beginning, ending);
        final var scalarValue = new ScalarValueJpa<>(1, Units.METERS);
        final var entity = new ScalarPropertyJpa<>(Util.randId(), scalarValue, Set.of(ind));

        em.getTransaction().begin();
        em.persist(beginning);
        em.persist(ending);
        em.persist(ind);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(ScalarPropertyJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertTrue(entity.getMembers().contains(ind));
        assertEquals(entity.getProperty(), found.getProperty());
    }

    @Test
    public void testSignifier() {
        final Language english = new LanguageJpa(Util.randId(), "English");
        final Resignified personNameFrom = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Resignified personNameTo = new ResignifiedJpa(Util.randId(), Instant.now(), Instant.now());
        final Signifier<String> entity = new SignifierJpa(Util.randId(), "Person Name 01", english, personNameFrom, personNameTo);

        em.getTransaction().begin();
        em.persist(english);
        em.persist(personNameFrom);
        em.persist(personNameTo);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(SignifierJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getLanguage(), found.getLanguage());
        assertEquals(entity.getName(), found.getName());
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getEnding(), found.getEnding());
    }

    @Test
    public void testSignifying() {
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

    @Test
    public void testState() {
        final var personFrom = new BirthJpa(Util.randId(), Instant.now(), Instant.now());
        final var personTo = new DeathJpa(Util.randId(), Instant.now(), Instant.now());
        final var person = new IndividualJpa<>(Util.randId(), personFrom, personTo);
        final var stateFrom = new CreatedJpa(Util.randId(), Instant.now(), Instant.now());
        final var stateTo = new DestroyedJpa(Util.randId(), Instant.now(), Instant.now());
        final var entity = new StateJpa<>(Util.randId(), person, stateFrom, stateTo);

        em.getTransaction().begin();
        em.persist(personFrom);
        em.persist(personTo);
        em.persist(stateFrom);
        em.persist(stateTo);
        em.persist(person);
        em.persist(entity);
        em.getTransaction().commit();

        final var found = em.find(StateJpa.class, entity.getIdentifier());
        assertNotNull(found);
        assertEquals(entity.getIdentifier(), found.getIdentifier());
        assertEquals(entity.getIndividual(), found.getIndividual());
        assertEquals(entity.getBeginning(), found.getBeginning());
        assertEquals(entity.getEnding(), found.getEnding());
    }

    @Test
    public void testTransferringOwnership() {
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
