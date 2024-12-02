package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.aosd.onto.events.Decommissioned;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "DECOMMISSIONED_EVENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class DecommissionedJpa extends EventJpa implements Decommissioned {
    public DecommissionedJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
