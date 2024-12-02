package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.aosd.onto.events.Assembled;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "ASSEMBLED_EVENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssembledJpa extends EventJpa implements Assembled {
    public AssembledJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
