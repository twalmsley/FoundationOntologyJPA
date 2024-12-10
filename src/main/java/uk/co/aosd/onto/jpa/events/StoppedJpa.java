package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Stopped;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity
@DiscriminatorValue("Stopped")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoppedJpa extends EventJpa implements Stopped {
    public StoppedJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
