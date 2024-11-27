package uk.co.aosd.onto.jpa;

import org.decimal4j.immutable.Decimal3f;
import uk.co.aosd.onto.money.Currency;
import uk.co.aosd.onto.money.MonetaryValue;

/**
 * An implementation of the MonetaryValue interface.
 *
 * @author Tony Walmsley
 */
public record MonetaryValueJpa<U extends Currency>(Decimal3f value, U unit) implements MonetaryValue<U> {

}
