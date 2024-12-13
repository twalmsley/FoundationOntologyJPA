package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Created;
import uk.co.aosd.onto.events.Deleted;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.PossibleWorld;

/**
 * An implementaton of the PossibleWorld interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "POSSIBLE_WORLD")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PossibleWorldJpa extends IndividualJpa<Created, Deleted> implements PossibleWorld {

    @ManyToMany(targetEntity = IndividualJpa.class, fetch = FetchType.LAZY)
    private Set<Individual<? extends Event, ? extends Event>> parts;

    /**
     * Constructor.
     */
    public PossibleWorldJpa(final String identifier, final Set<Individual<? extends Event, ? extends Event>> parts, final Created beginning,
        final Deleted ending) {
        super(identifier, beginning, ending);
        this.parts = parts;
    }
}
