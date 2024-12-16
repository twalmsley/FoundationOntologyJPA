package uk.co.aosd.onto.jpa;

import java.util.HashSet;
import java.util.Set;

import org.decimal4j.immutable.Decimal3f;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.foundation.Unit;
import uk.co.aosd.onto.jpa.events.AggregatedJpa;
import uk.co.aosd.onto.jpa.events.AppointedJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.CreatedJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.DeletedJpa;
import uk.co.aosd.onto.jpa.events.DisaggregatedJpa;
import uk.co.aosd.onto.jpa.events.DissolvedJpa;
import uk.co.aosd.onto.jpa.events.EventJpa;
import uk.co.aosd.onto.jpa.events.FormedJpa;
import uk.co.aosd.onto.jpa.events.RemovedJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.jpa.events.StartedJpa;
import uk.co.aosd.onto.jpa.events.StoppedJpa;
import uk.co.aosd.onto.jpa.events.TransferredFromJpa;
import uk.co.aosd.onto.jpa.events.TransferredToJpa;
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.model.Model;
import uk.co.aosd.onto.money.Currency;
import uk.co.aosd.onto.money.MonetaryValue;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * A reference implementation of the OntologyServices interface.
 *
 * <p>
 * This allows users of the library to code to the ontology interfaces without
 * knowing about the implementation classes directly.
 * </p>
 *
 * @author Tony Walmsley
 */
public class OntologyServicesJpa {

    public Language createLanguage(final String identifier, final String name) {
        return new LanguageJpa(identifier, name);
    }

    public SignifierJpa createSignifier(final String identifier, final String value, final Language language, final ResignifiedJpa from,
        final ResignifiedJpa to) {
        return new SignifierJpa(identifier, value.toString(), language, from, to);
    }

    public <T extends UniquelyIdentifiable> Class<T> createClass(final String identifier, final Set<T> members) {
        return new ClassJpa<T>(identifier, members);
    }

    public HumanJpa createHuman(final String identifier, final BirthJpa born, final DeathJpa died, final Class<Signifier<String, ResignifiedJpa>> names,
        final LanguageJpa nativeLanguage, final Class<LanguageJpa> languages, final DNA dna) {
        return new HumanJpa(identifier, born, died, names, nativeLanguage, languages, dna);
    }

    public MembershipJpa<RoleJpa> createMembership(final String identifier, final HumanJpa human, final RoleJpa role,
        final AppointedJpa from,
        final RemovedJpa to) {
        return new MembershipJpa<>(identifier, human, role, from, to);
    }

    public <R extends Role> OrganisationJpa<R> createOrganisation(final String identifier, final Class<MembershipJpa<R>> memberships, final String purpose,
        final Class<OrganisationJpa<R>> units, final Class<Signifier<String, ResignifiedJpa>> names, final FormedJpa from, final DissolvedJpa to) {
        return new OrganisationJpa<>(identifier, memberships, purpose, units, names, from, to);
    }

    public PossibleWorldJpa createPossibleWorld(final String identifier, final Set<Individual<? extends Event, ? extends Event>> parts,
        final CreatedJpa from, final DeletedJpa to) {
        return new PossibleWorldJpa(identifier, parts, from, to);
    }

    public <B extends EventJpa, E extends EventJpa, T extends Individual<B, E>> StateJpa<B, E, T> createState(final String identifier, final T individual,
        final B from, final E to) {
        return new StateJpa<B, E, T>(identifier, individual, from, to);
    }

    public DNA createDna(final String identifier, final String dna) {
        return new DNAJpa(identifier, dna);
    }

    public <A extends EventJpa, B extends EventJpa, C extends EventJpa, D extends EventJpa> OwningJpa<A, B, C, D> createOwnership(final String identifier,
        final String actionsDescription, final Individual<A, B> owner, final Individual<C, D> owned, final TransferredFromJpa from, final TransferredToJpa to) {
        return new OwningJpa<>(identifier, actionsDescription, owner, owned, from, to);
    }

    /**
     * Transfers ownership of an individual from one owner to another.
     */
    public <A extends EventJpa, B extends EventJpa, C extends EventJpa, D extends EventJpa> TransferringOfOwnershipJpa<A, B, C, D> transferOwnership(
        final String identifier,
        final String actionsDescription, final OwningJpa<A, B, C, D> current, final IndividualJpa<A, B> newOwner, final StartedJpa from, final StoppedJpa to) {
        // The previous owneship ends at the from event.
        final var transferredFromEvent = new TransferredFromJpa(Util.randId(), from.getFrom(), from.getTo());
        final var transferredToEvent = new TransferredToJpa(Util.randId(), to.getFrom(), to.getTo());
        ((OwningJpa<A, B, C, D>) current).setEnding(transferredToEvent);

        // The new ownership starts at the from event.
        final var ownershipEnds = new TransferredToJpa(Util.randId(), null, null);
        final var newOwnership = createOwnership(Util.randId(), actionsDescription, newOwner, current.getOwned(), transferredFromEvent, ownershipEnds);

        // The transfer happens at the from event and finishes at the from event.
        return new TransferringOfOwnershipJpa<>(identifier, actionsDescription, current, newOwnership, transferredFromEvent, transferredToEvent);
    }

    public Currency createCurrency(final String identifier, final String code, final String name, final char symbol) {
        return new CurrencyJpa(identifier, code, name, symbol);
    }

    public <U extends Currency> MonetaryValue<U> createMonetaryValue(final Decimal3f value, final U unit) {
        return new MonetaryValueJpa<U>(value, unit);
    }

    public Model createModel(final String identifier) {
        return new ModelJpa(identifier, new HashSet<>());
    }

    /**
     * Creates an agglomerate from a set of items.
     */
    @SuppressWarnings("unchecked")
    public AgglomerateJpa createAgglomerate(final String identifier, final Set<Individual<? extends EventJpa, ? extends EventJpa>> items,
        final AggregatedJpa from, final DisaggregatedJpa to) {

        final Set<IndividualJpa<? extends EventJpa, ? extends EventJpa>> parts = new HashSet<>();
        items.forEach(i -> parts.add((IndividualJpa<EventJpa, EventJpa>) i));
        return new AgglomerateJpa(identifier, parts, from, to);
    }

    public <N extends Number, U extends Unit, T> AggregateJpa<N, U, T> createAggregate(final String identifier, final java.lang.Class<T> kind,
        final ScalarValue<N, U> quantity, final AggregatedJpa from, final DisaggregatedJpa to) {
        return new AggregateJpa<>(identifier, kind, quantity, from, to);
    }

    public <N extends Number, U extends Unit> ScalarValue<N, U> createScalarValue(final N value, final U unit) {
        return new ScalarValueJpa<N, U>(value, unit);
    }
}
