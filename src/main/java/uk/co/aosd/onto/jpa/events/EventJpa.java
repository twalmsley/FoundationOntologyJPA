package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;

/**
 * An implementation of the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "EVENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "EVENT_TYPE", discriminatorType = DiscriminatorType.STRING)
public class EventJpa implements Event {
    @Id
    @Column(name = "IDENTIFIER", columnDefinition = "VARCHAR(36)")
    private String identifier;

    @Column(name = "BEGINNING", nullable = true)
    private Instant from;

    @Column(name = "ENDING", nullable = true)
    private Instant to;

}
