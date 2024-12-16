package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.jpa.events.EventJpa;
import uk.co.aosd.onto.jpa.events.TransferredFromJpa;
import uk.co.aosd.onto.jpa.events.TransferredToJpa;
import uk.co.aosd.onto.ownership.Owning;

/**
 * An implementation of the Owning interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "OWNING")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OwningJpa<A extends EventJpa, B extends EventJpa, C extends EventJpa, D extends EventJpa>
    extends IndividualJpa<TransferredFromJpa, TransferredToJpa>
    implements Owning<A, B, C, D, TransferredFromJpa, TransferredToJpa> {
    @Column(name = "ACTIONS_DESCRIPTION", columnDefinition = "TEXT")
    private String actionsDescription;

    @ManyToOne(targetEntity = IndividualJpa.class, fetch = FetchType.LAZY)
    private Individual<A, B> owner;

    @ManyToOne(targetEntity = IndividualJpa.class, fetch = FetchType.LAZY)
    private Individual<C, D> owned;

    /**
     * Constructor.
     */
    public OwningJpa(final String identifier, final String actionsDescription, final Individual<A, B> owner, final Individual<C, D> owned,
        final TransferredFromJpa beginning, final TransferredToJpa ending) {
        super(identifier, beginning, ending);
        this.actionsDescription = actionsDescription;
        this.owner = owner;
        this.owned = owned;
    }
}
