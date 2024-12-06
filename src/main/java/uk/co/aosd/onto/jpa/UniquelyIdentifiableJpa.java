package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
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
 *
 * @author Tony Walmsley
 */
@Entity(name = "UNIQUELY_IDENTIFIABLE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniquelyIdentifiableJpa implements UniquelyIdentifiable {
    @Id
    @Column(name = "IDENTIFIER", nullable = false, updatable = false, unique = true, length = 36, columnDefinition = "CHAR(36)")
    private String identifier;
}
