package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Appointed;
import uk.co.aosd.onto.events.Removed;
import uk.co.aosd.onto.foundation.Role;
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
public class MembershipJpa<R extends Role> extends IndividualJpa<Appointed, Removed> implements Membership<R> {
    @ManyToOne(targetEntity = HumanJpa.class, fetch = FetchType.LAZY)
    private Human member;

    @ManyToOne(targetEntity = RoleJpa.class, fetch = FetchType.LAZY)
    private R role;

    /**
     * Constructor.
     */
    public MembershipJpa(final String identifier, final Human member, final R role, final Appointed beginning, final Removed ending) {
        super(identifier, beginning, ending);
        this.member = member;
        this.role = role;
    }
}