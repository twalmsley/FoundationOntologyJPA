package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Destroyed;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity
@DiscriminatorValue("Destroyed")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DestroyedJpa extends EventJpa implements Destroyed {
    public DestroyedJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
