package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * An implementation of a role for an employee.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
class EmployeeRole extends RoleJpa {

    public EmployeeRole(final String identifier, final String name, final String description) {
        super(identifier, name);
    }
}