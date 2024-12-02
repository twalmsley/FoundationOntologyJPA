package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;

/**
 * An implementation of UniquelyIdentifiable.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniquelyIdentifiableJpa implements UniquelyIdentifiable {
    @Id
    private String identifier;
}
