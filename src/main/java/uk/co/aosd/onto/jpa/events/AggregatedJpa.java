package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.aosd.onto.events.Aggregated;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "AGGREGATED_EVENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class AggregatedJpa extends EventJpa implements Aggregated {
    public AggregatedJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
