package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Stopped;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "STOPPED_EVENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoppedJpa implements Stopped {
    @Id
    private String identifier;

    @Column(name = "beginning")
    private Instant from;

    @Column(name = "ending")
    private Instant to;

}
