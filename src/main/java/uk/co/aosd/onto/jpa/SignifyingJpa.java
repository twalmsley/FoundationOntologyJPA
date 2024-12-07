package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Resignified;
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
public class SignifyingJpa extends UniquelyIdentifiableJpa implements Signifying<String> {

    @Column(name = "ACTIONS_DESCRIPTION", nullable = false, updatable = false, columnDefinition = "TEXT")
    private String actionsDescription;

    @Column(name = "NAME", nullable = false, updatable = true, columnDefinition = "TEXT")
    private String name;

    @ManyToOne(targetEntity = LanguageJpa.class)
    private Language language;

    @ManyToOne(targetEntity = UniquelyIdentifiableJpa.class)
    private UniquelyIdentifiable named;

    @OneToOne(targetEntity = ResignifiedJpa.class)
    private Resignified beginning;

    @OneToOne(targetEntity = ResignifiedJpa.class)
    private Resignified ending;

    /**
     * Constructor.
     */
    public SignifyingJpa(final String identifier, final String actionsDescription, final String name, final Language language, final UniquelyIdentifiable named,
        final Resignified beginning, final Resignified ending) {
        super(identifier);
        this.actionsDescription = actionsDescription;
        this.name = name;
        this.language = language;
        this.named = named;
        this.beginning = beginning;
        this.ending = ending;
    }
}
