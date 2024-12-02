package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.aosd.onto.events.Installed;

/**
 * An implementation of the Installed interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "INSTALLED_EVENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class InstalledJpa extends EventJpa implements Installed {
    public InstalledJpa(final String identifier, final Instant from, final Instant to) {
        super(identifier, from, to);
    }
}
