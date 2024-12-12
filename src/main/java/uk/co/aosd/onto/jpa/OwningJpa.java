package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.TransferredFrom;
import uk.co.aosd.onto.events.TransferredTo;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
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
public class OwningJpa<A extends Event, B extends Event, C extends Event, D extends Event> extends IndividualJpa<TransferredFrom, TransferredTo>
    implements Owning<A, B, C, D> {
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
        final TransferredFrom beginning, final TransferredTo ending) {
        super(identifier, beginning, ending);
        this.actionsDescription = actionsDescription;
        this.owner = owner;
        this.owned = owned;
    }
}
