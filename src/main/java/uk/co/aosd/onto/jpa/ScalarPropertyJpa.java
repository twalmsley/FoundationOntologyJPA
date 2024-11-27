package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.ScalarProperty;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.foundation.Unit;

/**
 * An implementation of the ScalarProperty interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScalarPropertyJpa<T extends UniquelyIdentifiable, U extends Number, V extends Unit> implements ScalarProperty<T, U, V> {
    private String identifier;
    private ScalarValue<U, V> property;
    private Set<T> members;
}
