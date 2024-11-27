package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
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
    private String identifier;
    private String dna;

}
