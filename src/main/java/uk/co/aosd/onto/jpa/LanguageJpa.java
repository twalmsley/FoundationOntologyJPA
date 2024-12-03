package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.language.Language;

/**
 * An implementation of Language.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LanguageJpa extends UniquelyIdentifiableJpa implements Language {

    private String name;

    public LanguageJpa(final String identifier, final String name) {
        super(identifier);
        this.name = name;
    }
}
