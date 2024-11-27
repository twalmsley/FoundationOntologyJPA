package uk.co.aosd.onto.jpa;

import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.Unit;

/**
 * An implementation of the ScalarValue interface.
 *
 * @author Tony Walmsley
 */
public record ScalarValueJpa<T extends Number, U extends Unit>(T value, U unit) implements ScalarValue<T, U> {

}
