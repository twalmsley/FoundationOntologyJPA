package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Entity(name = "AGGLOMERATE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgglomerateJpa extends UniquelyIdentifiableJpa implements Agglomerate {

    @OneToMany
    @Column(name = "PARTS")
    private Set<IndividualJpa> parts;

    @OneToOne(targetEntity = AggregatedJpa.class)
    private Aggregated beginning;

    @OneToOne(targetEntity = DisaggregatedJpa.class)
    private Disaggregated ending;

    /**
     * Constructor.
     */
    public AgglomerateJpa(final String identifier, final Set<IndividualJpa> parts, final Aggregated beginning, final Disaggregated ending) {
        super(identifier);
        this.parts = parts;
        this.beginning = beginning;
        this.ending = ending;
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
