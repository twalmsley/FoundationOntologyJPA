package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;

/**
 * An implementation of Class of T.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassJpa<T extends UniquelyIdentifiable> implements Class<T> {
    @Id
    private String identifier;

    @OneToMany(targetEntity = UniquelyIdentifiableJpa.class, cascade = CascadeType.ALL)
    private Set<T> members;

}
