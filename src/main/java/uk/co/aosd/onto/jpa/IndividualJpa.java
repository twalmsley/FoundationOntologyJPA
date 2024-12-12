package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.jpa.events.EventJpa;

/**
 * An implementation of the Individual interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "INDIVIDUAL")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndividualJpa<B extends Event, E extends Event> extends UniquelyIdentifiableJpa implements Individual<B, E> {

    @ManyToOne(targetEntity = EventJpa.class, fetch = FetchType.LAZY)
    private B beginning;

    @ManyToOne(targetEntity = EventJpa.class, fetch = FetchType.LAZY)
    private E ending;

    /**
     * Constructor.
     */
    public IndividualJpa(final String identifier, final B beginning, final E ending) {
        super(identifier);
        this.beginning = beginning;
        this.ending = ending;
    }
}
