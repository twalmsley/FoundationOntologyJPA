package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Started;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "STARTED_EVENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartedJpa implements Started {
    @Id
    private String identifier;

    @Column(name = "beginning")
    private Instant from;

    @Column(name = "ending")
    private Instant to;

}
