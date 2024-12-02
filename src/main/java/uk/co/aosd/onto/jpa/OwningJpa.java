package uk.co.aosd.onto.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.TransferredFrom;
import uk.co.aosd.onto.events.TransferredTo;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.jpa.events.TransferredFromJpa;
import uk.co.aosd.onto.jpa.events.TransferredToJpa;
import uk.co.aosd.onto.ownership.Owning;

/**
 * An implementation of the Owning interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OwningJpa<A extends Event, B extends Event, C extends Event, D extends Event> extends UniquelyIdentifiableJpa implements Owning<A, B, C, D> {
    private String actionsDescription;

    @ManyToOne(targetEntity = IndividualJpa.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Individual<A, B> owner;

    @ManyToOne(targetEntity = IndividualJpa.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Individual<C, D> owned;

    @OneToOne(targetEntity = TransferredFromJpa.class, cascade = { CascadeType.ALL })
    private TransferredFrom beginning;

    @OneToOne(targetEntity = TransferredToJpa.class, cascade = { CascadeType.ALL })
    private TransferredTo ending;

    /**
     * Constructor.
     */
    public OwningJpa(final String identifier, final String actionsDescription, final Individual<A, B> owner, final Individual<C, D> owned,
        final TransferredFrom beginning, final TransferredTo ending) {
        super(identifier);
        this.actionsDescription = actionsDescription;
        this.owner = owner;
        this.owned = owned;
        this.beginning = beginning;
        this.ending = ending;
    }
}
