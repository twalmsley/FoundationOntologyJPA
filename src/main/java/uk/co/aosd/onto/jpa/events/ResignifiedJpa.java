package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.aosd.onto.events.Resignified;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "RESIGNIFIED_EVENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class ResignifiedJpa extends EventJpa implements Resignified {
    public ResignifiedJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
