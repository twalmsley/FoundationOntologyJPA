package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.signifying.Signifying;

/**
 * An implementation of Signifying.
 *
 * @author Tony Walmsley
 */
@Entity(name = "SIGNIFYING")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SignifyingJpa extends IndividualJpa<ResignifiedJpa, ResignifiedJpa> implements Signifying<String, ResignifiedJpa> {

    @Column(name = "ACTIONS_DESCRIPTION", columnDefinition = "TEXT")
    private String actionsDescription;

    @Column(name = "NAME", columnDefinition = "TEXT")
    private String name;

    @ManyToOne(targetEntity = LanguageJpa.class, fetch = FetchType.LAZY)
    private Language language;

    @ManyToOne(targetEntity = UniquelyIdentifiableJpa.class, fetch = FetchType.LAZY)
    private UniquelyIdentifiable named;

    /**
     * Constructor.
     */
    public SignifyingJpa(final String identifier, final String actionsDescription, final String name, final Language language, final UniquelyIdentifiable named,
        final ResignifiedJpa beginning, final ResignifiedJpa ending) {
        super(identifier, beginning, ending);
        this.actionsDescription = actionsDescription;
        this.name = name;
        this.language = language;
        this.named = named;
    }
}
