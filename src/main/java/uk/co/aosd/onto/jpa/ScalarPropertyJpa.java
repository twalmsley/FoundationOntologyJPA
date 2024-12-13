package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Target;
import uk.co.aosd.onto.foundation.ScalarProperty;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.foundation.Unit;
import uk.co.aosd.onto.jpa.converters.ScalarValueConverter;

/**
 * An implementation of the ScalarProperty interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "SCALAR_PROPERTY")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScalarPropertyJpa<T extends UniquelyIdentifiable, U extends Number, V extends Unit> extends UniquelyIdentifiableJpa
    implements ScalarProperty<T, U, V> {

    @Convert(converter = ScalarValueConverter.class)
    @Target(ScalarValueJpa.class)
    @Column(name = "PROPERTY", columnDefinition = "VARCHAR(255)")
    private ScalarValue<U, V> property;

    @ManyToMany(targetEntity = UniquelyIdentifiableJpa.class, fetch = FetchType.LAZY)
    private Set<T> members;

    /**
     * Constructor.
     */
    public ScalarPropertyJpa(final String identifier, final ScalarValue<U, V> property, final Set<T> members) {
        super(identifier);
        this.property = property;
        this.members = members;
    }
}
