package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.aosd.onto.events.TransferredTo;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "TRANSFERRED_TO_EVENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferredToJpa extends EventJpa implements TransferredTo {
    public TransferredToJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
