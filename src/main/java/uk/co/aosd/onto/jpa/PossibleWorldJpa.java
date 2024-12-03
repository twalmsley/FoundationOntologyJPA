package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Created;
import uk.co.aosd.onto.events.Deleted;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.PossibleWorld;
import uk.co.aosd.onto.jpa.events.CreatedJpa;
import uk.co.aosd.onto.jpa.events.DeletedJpa;

/**
 * An implementaton of the PossibleWorld interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PossibleWorldJpa extends UniquelyIdentifiableJpa implements PossibleWorld {

    @OneToMany(targetEntity = IndividualJpa.class, cascade = { CascadeType.ALL })
    private Set<Individual<? extends Event, ? extends Event>> parts;

    @OneToOne(targetEntity = CreatedJpa.class, cascade = { CascadeType.ALL })
    private Created beginning;

    @OneToOne(targetEntity = DeletedJpa.class, cascade = { CascadeType.ALL })
    private Deleted ending;

    /**
     * Constructor.
     */
    public PossibleWorldJpa(final String identifier, final Set<Individual<? extends Event, ? extends Event>> parts, final Created beginning,
        final Deleted ending) {
        super(identifier);
        this.parts = parts;
        this.beginning = beginning;
        this.ending = ending;
    }
}
