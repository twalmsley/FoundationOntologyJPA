package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.jpa.events.AppointedJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.RemovedJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
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
public class MembershipJpa<R extends Role> extends IndividualJpa<AppointedJpa, RemovedJpa>
    implements Membership<R, AppointedJpa, RemovedJpa, BirthJpa, DeathJpa, ResignifiedJpa, LanguageJpa> {

    @ManyToOne(targetEntity = HumanJpa.class, fetch = FetchType.LAZY)
    private Human<BirthJpa, DeathJpa, ResignifiedJpa, LanguageJpa> member;

    @ManyToOne(targetEntity = RoleJpa.class, fetch = FetchType.LAZY)
    private R role;

    /**
     * Constructor.
     */
    public MembershipJpa(final String identifier, final Human<BirthJpa, DeathJpa, ResignifiedJpa, LanguageJpa> member, final R role,
        final AppointedJpa beginning,
        final RemovedJpa ending) {
        super(identifier, beginning, ending);
        this.member = member;
        this.role = role;
    }
}