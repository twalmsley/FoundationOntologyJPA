package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Agglomerate;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.jpa.events.AggregatedJpa;
import uk.co.aosd.onto.jpa.events.DisaggregatedJpa;
import uk.co.aosd.onto.jpa.events.EventJpa;

/**
 * An implementation of the Agglomerate interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "AGGLOMERATE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgglomerateJpa extends IndividualJpa<AggregatedJpa, DisaggregatedJpa> implements Agglomerate<AggregatedJpa, DisaggregatedJpa> {

    @OneToMany(fetch = FetchType.LAZY)
    private Set<IndividualJpa<? extends EventJpa, ? extends EventJpa>> parts;

    /**
     * Constructor.
     */
    public AgglomerateJpa(final String identifier, final Set<IndividualJpa<? extends EventJpa, ? extends EventJpa>> parts, final AggregatedJpa beginning,
        final DisaggregatedJpa ending) {
        super(identifier, beginning, ending);
        this.parts = parts;
    }

    /**
     * Get the parts of this agglomerate.
     */
    public Set<Individual<? extends Event, ? extends Event>> getParts() {
        if (this.parts == null) {
            return Set.of();
        }
        final Set<Individual<? extends Event, ? extends Event>> parts = Set.copyOf(this.parts);
        return parts;
    }
}
