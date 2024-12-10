package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Deleted;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity
@DiscriminatorValue("Deleted")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeletedJpa extends EventJpa implements Deleted {
    public DeletedJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
