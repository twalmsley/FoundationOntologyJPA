package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.aosd.onto.events.Bought;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "BOUGHT_EVENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class BoughtJpa extends EventJpa implements Bought {
    public BoughtJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
