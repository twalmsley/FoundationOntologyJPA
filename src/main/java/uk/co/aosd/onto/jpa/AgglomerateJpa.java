package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Aggregated;
import uk.co.aosd.onto.events.Disaggregated;
import uk.co.aosd.onto.foundation.Agglomerate;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.jpa.events.AggregatedJpa;
import uk.co.aosd.onto.jpa.events.DisaggregatedJpa;

/**
 * An implementation of the Agglomerate interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgglomerateJpa implements Agglomerate {

    @Id
    private String identifier;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<IndividualJpa> parts;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = AggregatedJpa.class)
    private Aggregated beginning;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = DisaggregatedJpa.class)
    private Disaggregated ending;

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
