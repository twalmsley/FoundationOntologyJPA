package uk.co.aosd.onto.jpa.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.aosd.onto.jpa.MonetaryValueJpa;

/**
 * Converts a currency to a string and back again.
 */
@Converter
public class MonetaryValueConverter implements AttributeConverter<MonetaryValueJpa<?>, String> {

    @Override
    public String convertToDatabaseColumn(final MonetaryValueJpa<?> attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public MonetaryValueJpa<?> convertToEntityAttribute(final String dbData) {
        return dbData != null ? MonetaryValueJpa.valueOf(dbData) : null;
    }

}
