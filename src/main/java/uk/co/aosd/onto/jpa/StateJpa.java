package uk.co.aosd.onto.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.State;
import uk.co.aosd.onto.jpa.events.EventJpa;

/**
 * An implementation of the State interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "STATE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StateJpa<B extends Event, E extends Event, V extends Individual<B, E>> extends UniquelyIdentifiableJpa implements State<B, E, V> {

    @ManyToOne(targetEntity = IndividualJpa.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private V individual;

    @OneToOne(targetEntity = EventJpa.class, cascade = { CascadeType.ALL })
    private B beginning;

    @OneToOne(targetEntity = EventJpa.class, cascade = { CascadeType.ALL })
    private E ending;

    /**
     * Constructor.
     */
    public StateJpa(final String identifier, final V individual, final B beginning, final E ending) {
        super(identifier);
        this.individual = individual;
        this.beginning = beginning;
        this.ending = ending;
    }
}
