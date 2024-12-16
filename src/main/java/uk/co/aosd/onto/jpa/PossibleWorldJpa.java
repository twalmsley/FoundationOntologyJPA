package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.PossibleWorld;
import uk.co.aosd.onto.jpa.events.CreatedJpa;
import uk.co.aosd.onto.jpa.events.DeletedJpa;

/**
 * An implementaton of the PossibleWorld interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "POSSIBLE_WORLD")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PossibleWorldJpa extends IndividualJpa<CreatedJpa, DeletedJpa> implements PossibleWorld<CreatedJpa, DeletedJpa> {

    @ManyToMany(targetEntity = IndividualJpa.class, fetch = FetchType.LAZY)
    private Set<Individual<? extends Event, ? extends Event>> parts;

    /**
     * Constructor.
     */
    public PossibleWorldJpa(final String identifier, final Set<Individual<? extends Event, ? extends Event>> parts, final CreatedJpa beginning,
        final DeletedJpa ending) {
        super(identifier, beginning, ending);
        this.parts = parts;
    }
}
