package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Property;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;

/**
 * An implementation of the Property interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyJpa<T extends UniquelyIdentifiable, U> implements Property<T, U> {
    private String identifier;
    private Set<T> members;
    private U property;
}
