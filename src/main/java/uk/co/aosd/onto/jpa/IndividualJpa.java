package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.jpa.events.EventJpa;

/**
 * An implementation of the Individual interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "INDIVIDUAL")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
public class IndividualJpa<B extends Event, E extends Event> implements Individual<B, E> {
    @Id
    @Column(name = "IDENTIFIER", nullable = false, updatable = false, unique = true, length = 36, columnDefinition = "VARCHAR(36)")
    private String identifier;

    @OneToOne(targetEntity = EventJpa.class)
    private B beginning;

    @OneToOne(targetEntity = EventJpa.class)
    private E ending;

    /**
     * Constructor.
     */
    public IndividualJpa(final String identifier, final B beginning, final E ending) {
        this.identifier = identifier;
        this.beginning = beginning;
        this.ending = ending;
    }
}
