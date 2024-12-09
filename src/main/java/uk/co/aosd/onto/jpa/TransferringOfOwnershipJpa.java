package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.TransferredFrom;
import uk.co.aosd.onto.events.TransferredTo;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.ownership.Owning;
import uk.co.aosd.onto.ownership.TransferringOfOwnership;

/**
 * An implementation of the TransferringOfOwnership interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "TRANSFERRING_OF_OWNERSHIP")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransferringOfOwnershipJpa<A extends Event, B extends Event, C extends Event, D extends Event>
    extends IndividualJpa<TransferredFrom, TransferredTo> implements TransferringOfOwnership<A, B, C, D> {

    @Column(name = "ACTIONS_DESCRIPTION", nullable = false, updatable = false, columnDefinition = "TEXT")
    private String actionsDescription;

    @ManyToOne(targetEntity = OwningJpa.class)
    private Owning<A, B, C, D> from;

    @ManyToOne(targetEntity = OwningJpa.class)
    private Owning<A, B, C, D> to;

    /**
     * Constructor.
     */
    public TransferringOfOwnershipJpa(final String identifier, final String actionsDescription, final Owning<A, B, C, D> from, final Owning<A, B, C, D> to,
        final TransferredFrom beginning, final TransferredTo ending) {
        super(identifier, beginning, ending);
        this.actionsDescription = actionsDescription;
        this.from = from;
        this.to = to;
    }
}
