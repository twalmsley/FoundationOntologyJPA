package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Birth;
import uk.co.aosd.onto.events.Death;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.language.Language;
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
public class HumanJpa extends UniquelyIdentifiableJpa implements Human {
    @OneToOne(targetEntity = BirthJpa.class)
    private Birth beginning;

    @OneToOne(targetEntity = DeathJpa.class)
    private Death ending;

    @OneToOne(targetEntity = ClassJpa.class)
    private Class<Signifier<String>> names;

    @ManyToOne(targetEntity = LanguageJpa.class)
    private Language nativeLanguage;

    @OneToOne(targetEntity = ClassJpa.class)
    private Class<Language> languages;

    @OneToOne(targetEntity = DNAJpa.class)
    private DNA dna;

    /**
     * Constructor.
     */
    public HumanJpa(final String identifier, final Birth beginning, final Death ending, final Class<Signifier<String>> names, final Language nativeLanguage,
        final Class<Language> languages, final DNA dna) {
        super(identifier);
        this.beginning = beginning;
        this.ending = ending;
        this.names = names;
        this.nativeLanguage = nativeLanguage;
        this.languages = languages;
        this.dna = dna;
    }
}
