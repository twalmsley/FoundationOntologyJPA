package uk.co.aosd.onto.jpa.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts a Class to a String.
 *
 * @author Tony Walmsley
 */
@Converter
public class ClassConverter implements AttributeConverter<Class<?>, String> {

    @Override
    public String convertToDatabaseColumn(final Class<?> attribute) {
        return attribute != null ? attribute.getName() : null;
    }

    @Override
    public Class<?> convertToEntityAttribute(final String dbData) {
        try {
            return dbData != null ? Class.forName(dbData) : null;
        } catch (final ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid class name: " + dbData, e);
        }
    }
}
