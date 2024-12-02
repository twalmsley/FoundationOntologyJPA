package uk.co.aosd.onto.jpa.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.aosd.onto.jpa.ScalarValueJpa;

/**
 * Converts a scalar value to a string.
 */
@Converter
public class ScalarValueConverter implements AttributeConverter<ScalarValueJpa<?, ?>, String> {

    @Override
    public String convertToDatabaseColumn(final ScalarValueJpa<?, ?> attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public ScalarValueJpa<?, ?> convertToEntityAttribute(final String dbData) {
        return dbData != null ? ScalarValueJpa.valueOf(dbData) : null;
    }

}
