package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Appointed;
import uk.co.aosd.onto.events.Removed;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.jpa.events.AppointedJpa;
import uk.co.aosd.onto.jpa.events.RemovedJpa;
import uk.co.aosd.onto.organisation.Membership;

/**
 * An implementation of the Membership interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "MEMBERSHIP")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MembershipJpa<R extends Role> extends UniquelyIdentifiableJpa implements Membership<R> {
    @ManyToOne(targetEntity = HumanJpa.class)
    private Human member;

    @ManyToOne(targetEntity = RoleJpa.class)
    private R role;

    @OneToOne(targetEntity = AppointedJpa.class)
    private Appointed beginning;

    @OneToOne(targetEntity = RemovedJpa.class)
    private Removed ending;

    /**
     * Constructor.
     */
    public MembershipJpa(final String identifier, final Human member, final R role, final Appointed beginning, final Removed ending) {
        super(identifier);
        this.member = member;
        this.role = role;
        this.beginning = beginning;
        this.ending = ending;
    }
}