package uk.co.aosd.onto.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.decimal4j.immutable.Decimal3f;
import uk.co.aosd.onto.money.Currency;
import uk.co.aosd.onto.money.MonetaryValue;
import uk.co.aosd.onto.units.Units;

/**
 * An implementation of the MonetaryValue interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonetaryValueJpa<U extends Currency> implements MonetaryValue<U> {
    private Decimal3f value;
    private U unit;

    /**
     * Create a new MonetaryValue from a Stringified value.
     *
     * @param dbData
     *            String-form of the value.
     * @return a new MonetaryValue.
     */
    public static MonetaryValueJpa<?> valueOf(final String dbData) {
        final String[] parts = dbData.split("/");

        switch (parts[1]) {
            case "USDollars":
                return new MonetaryValueJpa<Units.Dollars>(Decimal3f.valueOf(parts[0]), Units.DOLLARS);
            case "Euros":
                return new MonetaryValueJpa<Units.Euros>(Decimal3f.valueOf(parts[0]), Units.EUROS);
            case "PoundsSterling":
                return new MonetaryValueJpa<Units.PoundsSterling>(Decimal3f.valueOf(parts[0]), Units.POUNDS_STERLING);
            default:
                break;
        }
        throw new IllegalStateException("Unexpected value: " + parts[1]);
    }

    @Override
    public String toString() {
        return value.doubleValue() + "/" + unit.getIdentifier();
    }
}
