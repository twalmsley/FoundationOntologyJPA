package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * An example implementation of the Human interface. This may need to change.
 *
 * @author Tony Walmsley
 */
@Entity(name = "HUMAN")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HumanJpa extends IndividualJpa<BirthJpa, DeathJpa> implements Human<BirthJpa, DeathJpa, ResignifiedJpa, LanguageJpa> {

    @OneToOne(targetEntity = ClassJpa.class, fetch = FetchType.LAZY)
    private Class<Signifier<String, ResignifiedJpa>> names;

    @ManyToOne(targetEntity = LanguageJpa.class, fetch = FetchType.LAZY)
    private LanguageJpa nativeLanguage;

    @OneToOne(targetEntity = ClassJpa.class, fetch = FetchType.LAZY)
    private Class<LanguageJpa> languages;

    @OneToOne(targetEntity = DNAJpa.class, fetch = FetchType.LAZY)
    private DNA dna;

    /**
     * Constructor.
     */
    public HumanJpa(final String identifier, final BirthJpa beginning, final DeathJpa ending, final Class<Signifier<String, ResignifiedJpa>> names,
        final LanguageJpa nativeLanguage, final Class<LanguageJpa> languages, final DNA dna) {
        super(identifier, beginning, ending);
        this.names = names;
        this.nativeLanguage = nativeLanguage;
        this.languages = languages;
        this.dna = dna;
    }

}
