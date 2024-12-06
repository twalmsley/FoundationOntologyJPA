package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Role;

/**
 * An implementation of Role.
 *
 * @author Tony Walmsley
 */
@Entity(name = "ROLE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleJpa extends UniquelyIdentifiableJpa implements Role {

    @Column(name = "NAME", nullable = false, updatable = true, columnDefinition = "VARCHAR(255)")
    private String name;

    public RoleJpa(final String identifier, final String name) {
        super(identifier);
        this.name = name;
    }

}
