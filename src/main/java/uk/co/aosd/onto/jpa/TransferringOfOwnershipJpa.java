package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.jpa.events.EventJpa;
import uk.co.aosd.onto.jpa.events.TransferredFromJpa;
import uk.co.aosd.onto.jpa.events.TransferredToJpa;
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
public class TransferringOfOwnershipJpa<A extends EventJpa, B extends EventJpa, C extends EventJpa, D extends EventJpa>
    extends IndividualJpa<TransferredFromJpa, TransferredToJpa> implements TransferringOfOwnership<A, B, C, D, TransferredFromJpa, TransferredToJpa> {

    @Column(name = "ACTIONS_DESCRIPTION", columnDefinition = "TEXT")
    private String actionsDescription;

    @ManyToOne(targetEntity = OwningJpa.class, fetch = FetchType.LAZY)
    private OwningJpa<A, B, C, D> from;

    @ManyToOne(targetEntity = OwningJpa.class, fetch = FetchType.LAZY)
    private OwningJpa<A, B, C, D> to;

    /**
     * Constructor.
     */
    public TransferringOfOwnershipJpa(final String identifier, final String actionsDescription, final OwningJpa<A, B, C, D> from,
        final OwningJpa<A, B, C, D> to,
        final TransferredFromJpa beginning, final TransferredToJpa ending) {
        super(identifier, beginning, ending);
        this.actionsDescription = actionsDescription;
        this.from = from;
        this.to = to;
    }
}
