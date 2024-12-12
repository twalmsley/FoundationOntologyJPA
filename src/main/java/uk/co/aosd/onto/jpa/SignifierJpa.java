package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "SIGNIFIER")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SignifierJpa extends IndividualJpa<Resignified, Resignified> implements Signifier<String> {

    @Column(name = "NAME", columnDefinition = "TEXT")
    private String name;

    @ManyToOne(targetEntity = LanguageJpa.class, fetch = FetchType.LAZY)
    private Language language;

    /**
     * Constructor.
     */
    public SignifierJpa(final String identifier, final String name, final Language language, final Resignified beginning, final Resignified ending) {
        super(identifier, beginning, ending);
        this.name = name;
        this.language = language;
    }
}
