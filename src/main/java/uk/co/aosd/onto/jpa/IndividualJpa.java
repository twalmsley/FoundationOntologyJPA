package uk.co.aosd.onto.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.jpa.events.EventJpa;

/**
 * An implementation of the Individual interface.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualJpa implements Individual<Event, Event> {
    @Id
    private String identifier;

    @OneToOne(targetEntity = EventJpa.class, cascade = { CascadeType.ALL })
    private EventJpa beginning;

    @OneToOne(targetEntity = EventJpa.class, cascade = { CascadeType.ALL })
    private EventJpa ending;
}
