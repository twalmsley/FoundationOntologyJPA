package uk.co.aosd.onto.jpa;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Entity(name = "PROPERTY")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyJpa<T extends UniquelyIdentifiable, U extends Serializable> extends UniquelyIdentifiableJpa implements Property<T, U> {

    @OneToMany(targetEntity = UniquelyIdentifiableJpa.class, fetch = FetchType.LAZY)
    private Set<T> members;

    @Convert(converter = SerializableConverter.class)
    @Target(Serializable.class)
    @Column(name = "PROPERTY", columnDefinition = "BYTEA")
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
