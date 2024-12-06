package uk.co.aosd.onto.jpa.converters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * A converter for serializable objects.
 *
 * @author Tony Walmsley
 */
@Converter
public class SerializableConverter implements AttributeConverter<Serializable, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(final Serializable attribute) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(attribute);
            return bos.toByteArray();
        } catch (final IOException e) {
            throw new IllegalArgumentException("Could not convert attribute to byte array", e);
        }
    }

    @Override
    public Serializable convertToEntityAttribute(final byte[] dbData) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(dbData);
            ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Serializable) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Could not convert byte array to attribute", e);
        }
    }

}
