package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.DNA;

/**
 * An implementation of the DNA interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DNAJpa implements DNA {
    @Id
    private String identifier;

    @Column(name = "DNA_VALUE")
    private String dna;

}
