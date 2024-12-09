package uk.co.aosd.onto.jpa;

import java.util.HashSet;
import java.util.Set;

import org.decimal4j.immutable.Decimal3f;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Aggregated;
import uk.co.aosd.onto.events.Appointed;
import uk.co.aosd.onto.events.Birth;
import uk.co.aosd.onto.events.Created;
import uk.co.aosd.onto.events.Death;
import uk.co.aosd.onto.events.Deleted;
import uk.co.aosd.onto.events.Disaggregated;
import uk.co.aosd.onto.events.Dissolved;
import uk.co.aosd.onto.events.Formed;
import uk.co.aosd.onto.events.Removed;
import uk.co.aosd.onto.events.Resignified;
import uk.co.aosd.onto.events.Started;
import uk.co.aosd.onto.events.Stopped;
import uk.co.aosd.onto.events.TransferredFrom;
import uk.co.aosd.onto.events.TransferredTo;
import uk.co.aosd.onto.foundation.Agglomerate;
import uk.co.aosd.onto.foundation.Aggregate;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.PossibleWorld;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.State;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.foundation.Unit;
import uk.co.aosd.onto.jpa.events.AggregatedJpa;
import uk.co.aosd.onto.jpa.events.DisaggregatedJpa;
import uk.co.aosd.onto.jpa.events.EventJpa;
import uk.co.aosd.onto.jpa.events.TransferredFromJpa;
import uk.co.aosd.onto.jpa.events.TransferredToJpa;
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.model.Model;
import uk.co.aosd.onto.money.Currency;
import uk.co.aosd.onto.money.MonetaryValue;
import uk.co.aosd.onto.organisation.Membership;
import uk.co.aosd.onto.organisation.Organisation;
import uk.co.aosd.onto.ownership.Owning;
import uk.co.aosd.onto.ownership.TransferringOfOwnership;
import uk.co.aosd.onto.services.OntologyServices;
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
public class OntologyServicesJpa implements OntologyServices {

    @Override
    public Language createLanguage(final String identifier, final String name) {
        return new LanguageJpa(identifier, name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Signifier<T> createSignifier(final String identifier, final T value, final Language language, final Resignified from, final Resignified to) {
        return (Signifier<T>) new SignifierJpa(identifier, value.toString(), language, from, to);
    }

    @Override
    public <T extends UniquelyIdentifiable> Class<T> createClass(final String identifier, final Set<T> members) {
        return new ClassJpa<T>(identifier, members);
    }

    @Override
    public Human createHuman(final String identifier, final Birth born, final Death died, final Class<Signifier<String>> names, final Language nativeLanguage,
        final Class<Language> languages, final DNA dna) {
        return new HumanJpa(identifier, born, died, names, nativeLanguage, languages, dna);
    }

    @Override
    public <R extends Role> Membership<R> createMembership(final String identifier, final Human human, final R role, final Appointed from, final Removed to) {
        return new MembershipJpa<>(identifier, human, role, from, to);
    }

    @Override
    public <R extends Role> Organisation createOrganisation(final String identifier, final Class<Membership<R>> memberships, final String purpose,
        final Class<Organisation> units, final Class<Signifier<String>> names, final Formed from, final Dissolved to) {
        return new OrganisationJpa<>(identifier, memberships, purpose, units, names, from, to);
    }

    @Override
    public PossibleWorld createPossibleWorld(final String identifier, final Set<Individual<? extends Event, ? extends Event>> parts, final Created from,
        final Deleted to) {
        return new PossibleWorldJpa(identifier, parts, from, to);
    }

    @Override
    public <B extends Event, E extends Event, T extends Individual<B, E>> State<B, E, T> createState(final String identifier, final T individual,
        final B from, final E to) {
        return new StateJpa<B, E, T>(identifier, individual, from, to);
    }

    @Override
    public DNA createDna(final String identifier, final String dna) {
        return new DNAJpa(identifier, dna);
    }

    @Override
    public <A extends Event, B extends Event, C extends Event, D extends Event> Owning<A, B, C, D> createOwnership(final String identifier,
        final String actionsDescription, final Individual<A, B> owner, final Individual<C, D> owned, final TransferredFrom from, final TransferredTo to) {
        return new OwningJpa<>(identifier, actionsDescription, owner, owned, from, to);
    }

    @Override
    public <A extends Event, B extends Event, C extends Event, D extends Event> TransferringOfOwnership<A, B, C, D> transferOwnership(final String identifier,
        final String actionsDescription, final Owning<A, B, C, D> current, final Individual<A, B> newOwner, final Started from, final Stopped to) {
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

    @Override
    public Currency createCurrency(final String identifier, final String code, final String name, final char symbol) {
        return new CurrencyJpa(identifier, code, name, symbol);
    }

    @Override
    public <U extends Currency> MonetaryValue<U> createMonetaryValue(final Decimal3f value, final U unit) {
        return new MonetaryValueJpa<U>(value, unit);
    }

    @Override
    public Model createModel(final String identifier) {
        return new ModelJpa(identifier, new HashSet<>());
    }

    @Override
    public Agglomerate createAgglomerate(final String identifier, final Set<Individual<? extends Event, ? extends Event>> items, final Aggregated from,
        final Disaggregated to) {
        final Set<IndividualJpa<EventJpa, EventJpa>> parts = new HashSet<>();
        items.forEach(i -> parts.add((IndividualJpa<EventJpa, EventJpa>) i));
        return new AgglomerateJpa(identifier, parts, (AggregatedJpa) from, (DisaggregatedJpa) to);
    }

    @Override
    public <N extends Number, U extends Unit, T> Aggregate<N, U, T> createAggregate(final String identifier, final java.lang.Class<T> kind,
        final ScalarValue<N, U> quantity, final Aggregated from, final Disaggregated to) {
        return new AggregateJpa<>(identifier, kind, quantity, from, to);
    }

    @Override
    public <N extends Number, U extends Unit> ScalarValue<N, U> createScalarValue(final N value, final U unit) {
        return new ScalarValueJpa<N, U>(value, unit);
    }
}
