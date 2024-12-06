package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;

/**
 * An implementation of Class of T.
 *
 * @author Tony Walmsley
 */
@Entity(name = "CLASS")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClassJpa<T extends UniquelyIdentifiable> extends UniquelyIdentifiableJpa implements Class<T> {

    @OneToMany(targetEntity = UniquelyIdentifiableJpa.class)
    private Set<T> members;

    public ClassJpa(final String identifier, final Set<T> members) {
        super(identifier);
        this.members = members;
    }
}
