package uk.co.aosd.onto.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.Unit;
import uk.co.aosd.onto.units.Units;

/**
 * An implementation of the ScalarValue interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScalarValueJpa<T extends Number, U extends Unit> implements ScalarValue<T, U> {
    private T value;
    private U unit;

    /**
     * Create a new ScalarValue from a Stringified value.
     *
     * @param dbData
     *            String-form of the value.
     * @return a new ScalarValue.
     */
    public static ScalarValueJpa<?, ?> valueOf(final String dbData) {
        final String[] parts = dbData.split("/");
        final Unit units = switch (parts[1]) {
            case "KilogramUnits" -> Units.KILOGRAMS;
            case "MetersUnits" -> Units.METERS;
            case "SecondsTimeUnit" -> Units.SECONDS;
            case "USDollars" -> Units.DOLLARS;
            case "Euros" -> Units.EUROS;
            case "PoundsSterling" -> Units.POUNDS_STERLING;
            default -> throw new IllegalStateException("Unexpected value: " + parts[1]);
        };

        return new ScalarValueJpa<>(Double.parseDouble(parts[0]), units);
    }

    @Override
    public String toString() {
        return value.doubleValue() + "/" + unit.getIdentifier();
    }
}
