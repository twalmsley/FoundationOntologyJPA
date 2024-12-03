package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.jpa.UniquelyIdentifiableJpa;

/**
 * An implementation of the Event interface.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorColumn(name = "EVENT_TYPE", discriminatorType = DiscriminatorType.STRING)
public class EventJpa extends UniquelyIdentifiableJpa implements Event {
    @Column(name = "beginning")
    private Instant from;

    @Column(name = "ending")
    private Instant to;

    /**
     * Constructor.
     */
    public EventJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier);
        this.from = from;
        this.to = to;
    }
}
