package uk.co.aosd.onto.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.jpa.events.EventJpa;

/**
 * An implementation of the Individual interface.
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndividualJpa extends UniquelyIdentifiableJpa implements Individual<Event, Event> {
    @OneToOne(targetEntity = EventJpa.class, cascade = { CascadeType.ALL })
    private EventJpa beginning;

    @OneToOne(targetEntity = EventJpa.class, cascade = { CascadeType.ALL })
    private EventJpa ending;

    /**
     * Constructor.
     */
    public IndividualJpa(final String identifier, final EventJpa beginning, final EventJpa ending) {
        super(identifier);
        this.beginning = beginning;
        this.ending = ending;
    }
}
