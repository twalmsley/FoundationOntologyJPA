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
import uk.co.aosd.onto.jpa.events.TransferredFromJpa;
import uk.co.aosd.onto.jpa.events.TransferredToJpa;
import uk.co.aosd.onto.ownership.Owning;
import uk.co.aosd.onto.ownership.TransferringOfOwnership;

/**
 * An implementation of the TransferringOfOwnership interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransferringOfOwnershipJpa<A extends Event, B extends Event, C extends Event, D extends Event> extends UniquelyIdentifiableJpa
    implements TransferringOfOwnership<A, B, C, D> {
    private String actionsDescription;

    @ManyToOne(targetEntity = OwningJpa.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Owning<A, B, C, D> from;

    @ManyToOne(targetEntity = OwningJpa.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Owning<A, B, C, D> to;

    @OneToOne(targetEntity = TransferredFromJpa.class, cascade = { CascadeType.ALL })
    private TransferredFrom beginning;

    @OneToOne(targetEntity = TransferredToJpa.class, cascade = { CascadeType.ALL })
    private TransferredTo ending;

    /**
     * Constructor.
     */
    public TransferringOfOwnershipJpa(final String identifier, final String actionsDescription, final Owning<A, B, C, D> from, final Owning<A, B, C, D> to,
        final TransferredFrom beginning, final TransferredTo ending) {
        super(identifier);
        this.actionsDescription = actionsDescription;
        this.from = from;
        this.to = to;
        this.beginning = beginning;
        this.ending = ending;
    }
}
