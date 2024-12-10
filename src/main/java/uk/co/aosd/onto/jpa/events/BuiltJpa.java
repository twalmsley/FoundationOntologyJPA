package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Built;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity
@DiscriminatorValue("Built")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BuiltJpa extends EventJpa implements Built {
    public BuiltJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
