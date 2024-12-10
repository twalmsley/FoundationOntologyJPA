package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Changed;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity
@DiscriminatorValue("Changed")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChangedJpa extends EventJpa implements Changed {
    public ChangedJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
