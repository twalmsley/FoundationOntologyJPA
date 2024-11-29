package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Installed;

/**
 * An implementation of the Installed interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "INSTALLED_EVENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstalledJpa implements Installed {
    @Id
    private String identifier;

    @Column(name = "beginning")
    private Instant from;

    @Column(name = "ending")
    private Instant to;

}
