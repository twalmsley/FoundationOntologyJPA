package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Started;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity
@DiscriminatorValue("Started")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StartedJpa extends EventJpa implements Started {
    public StartedJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
