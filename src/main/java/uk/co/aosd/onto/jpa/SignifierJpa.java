package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Resignified;
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * An implementation of Signifier.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SignifierJpa<T> extends UniquelyIdentifiableJpa implements Signifier<T> {
    private String identifier;
    private T name;
    private Language language;
    private Resignified beginning;
    private Resignified ending;

    /**
     * Constructor.
     */
    public SignifierJpa(final String identifier, final T name, final Language language, final Resignified beginning, final Resignified ending) {
        super(identifier);
        this.name = name;
        this.language = language;
        this.beginning = beginning;
        this.ending = ending;
    }
}
