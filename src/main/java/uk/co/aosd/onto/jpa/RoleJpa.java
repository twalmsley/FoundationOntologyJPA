package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Role;

/**
 * An implementation of Role.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleJpa extends UniquelyIdentifiableJpa implements Role {
    private String name;

    public RoleJpa(final String identifier, final String name) {
        super(identifier);
        this.name = name;
    }

}
