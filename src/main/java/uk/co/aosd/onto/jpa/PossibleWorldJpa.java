package uk.co.aosd.onto.jpa;

import java.util.Set;

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
public record PossibleWorldJpa(String identifier, Set<Individual<? extends Event, ? extends Event>> parts, Created beginning,
    Deleted ending) implements PossibleWorld {
    public PossibleWorldJpa {
        ensureValid(beginning, ending);
    }
}
