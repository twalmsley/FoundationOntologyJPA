package uk.co.aosd.onto.jpa.events;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Assembled;

/**
 * An implementation of an extension to the Event interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssembledJpa implements Assembled {
    @Id
    private String identifier;

    @Column(name = "beginning")
    private Instant from;

    @Column(name = "ending")
    private Instant to;

}
