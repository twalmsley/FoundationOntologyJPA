package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.aosd.onto.events.Disaggregated;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "DISAGGREGATED_EVENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class DisaggregatedJpa extends EventJpa implements Disaggregated {
    public DisaggregatedJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
