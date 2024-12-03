package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;

/**
 * An implementation of UniquelyIdentifiable.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniquelyIdentifiableJpa implements UniquelyIdentifiable {
    @Id
    private String identifier;
}
