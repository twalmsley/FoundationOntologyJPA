Jpa uk.co.aosd.onto.jpa;

import java.util.Set;

import uk.co.aosd.onto.foundation.Property;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;

/**
 * An implementation of the Property interface.
 *
 * @author Tony Walmsley
 */
public record PropertyJpa<T extends UniquelyIdentifiable, U>(String identifier, Set<T> members, U property)
    implements Property<T, U> {

}
