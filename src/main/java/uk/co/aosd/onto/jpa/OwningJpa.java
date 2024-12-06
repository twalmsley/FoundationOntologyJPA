package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
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
@Entity(name = "OWNING")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OwningJpa<A extends Event, B extends Event, C extends Event, D extends Event> extends UniquelyIdentifiableJpa implements Owning<A, B, C, D> {
    @Column(name = "ACTIONS_DESCRIPTION", nullable = false, updatable = false, columnDefinition = "LONGTEXT")
    private String actionsDescription;

    @ManyToOne(targetEntity = IndividualJpa.class)
    private Individual<A, B> owner;

    @ManyToOne(targetEntity = IndividualJpa.class)
    private Individual<C, D> owned;

    @OneToOne(targetEntity = TransferredFromJpa.class)
    private TransferredFrom beginning;

    @OneToOne(targetEntity = TransferredToJpa.class)
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
