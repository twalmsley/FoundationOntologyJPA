package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Aggregated;
import uk.co.aosd.onto.events.Disaggregated;
import uk.co.aosd.onto.foundation.Agglomerate;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
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
public class AgglomerateJpa extends IndividualJpa<Aggregated, Disaggregated> implements Agglomerate {

    @OneToMany(fetch = FetchType.LAZY)
    private Set<IndividualJpa<EventJpa, EventJpa>> parts;

    /**
     * Constructor.
     */
    public AgglomerateJpa(final String identifier, final Set<IndividualJpa<EventJpa, EventJpa>> parts, final Aggregated beginning, final Disaggregated ending) {
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
