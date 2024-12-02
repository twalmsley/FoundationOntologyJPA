package uk.co.aosd.onto.jpa;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Target;
import uk.co.aosd.onto.foundation.Property;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.jpa.converters.SerializableConverter;

/**
 * An implementation of the Property interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyJpa<T extends UniquelyIdentifiable, U extends Serializable> extends UniquelyIdentifiableJpa implements Property<T, U> {

    @OneToMany(targetEntity = UniquelyIdentifiableJpa.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<T> members;

    @Convert(converter = SerializableConverter.class)
    @Target(Serializable.class)
    private U property;

    /**
     * Constructor.
     */
    public PropertyJpa(final String identifier, final Set<T> members, final U property) {
        super(identifier);
        this.members = members;
        this.property = property;
    }
}
