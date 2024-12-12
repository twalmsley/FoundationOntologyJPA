package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.State;

/**
 * An implementation of the State interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "STATE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StateJpa<B extends Event, E extends Event, V extends Individual<?, ?>> extends IndividualJpa<B, E> implements State<B, E, V> {

    @ManyToOne(targetEntity = IndividualJpa.class, fetch = FetchType.LAZY)
    private V individual;

    /**
     * Constructor.
     */
    public StateJpa(final String identifier, final V individual, final B beginning, final E ending) {
        super(identifier, beginning, ending);
        this.individual = individual;
    }
}
