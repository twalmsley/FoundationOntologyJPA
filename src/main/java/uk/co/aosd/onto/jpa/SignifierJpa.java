package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Resignified;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
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
public class SignifierJpa extends UniquelyIdentifiableJpa implements Signifier<String> {

    @Column(name = "NAME", nullable = false, updatable = true, columnDefinition = "LONGTEXT")
    private String name;

    @ManyToOne(targetEntity = LanguageJpa.class)
    private Language language;

    @OneToOne(targetEntity = ResignifiedJpa.class)
    private Resignified beginning;

    @OneToOne(targetEntity = ResignifiedJpa.class)
    private Resignified ending;

    /**
     * Constructor.
     */
    public SignifierJpa(final String identifier, final String name, final Language language, final Resignified beginning, final Resignified ending) {
        super(identifier);
        this.name = name;
        this.language = language;
        this.beginning = beginning;
        this.ending = ending;
    }
}
