package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.DNA;

/**
 * An implementation of the DNA interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "DNA")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DNAJpa extends UniquelyIdentifiableJpa implements DNA {

    @Column(name = "DNA_VALUE", nullable = false, updatable = false, columnDefinition = "TEXT")
    private String dna;

    /**
     * Constructor.
     */
    public DNAJpa(final String identifier, final String dna) {
        super(identifier);
        this.dna = dna;
    }
}
