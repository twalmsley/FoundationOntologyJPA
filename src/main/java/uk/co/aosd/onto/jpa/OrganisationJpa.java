package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.jpa.events.DissolvedJpa;
import uk.co.aosd.onto.jpa.events.FormedJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.organisation.Organisation;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * An implementation of the Organisation interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "ORGANISATION")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrganisationJpa<R extends Role> extends IndividualJpa<FormedJpa, DissolvedJpa> implements Organisation<FormedJpa, DissolvedJpa, ResignifiedJpa> {
    @OneToOne(targetEntity = ClassJpa.class, fetch = FetchType.LAZY)
    private Class<MembershipJpa<R>> members;

    @Column(name = "PURPOSE", columnDefinition = "TEXT")
    private String purpose;

    @OneToOne(targetEntity = ClassJpa.class, fetch = FetchType.LAZY)
    private Class<OrganisationJpa<R>> units;

    @OneToOne(targetEntity = ClassJpa.class, fetch = FetchType.LAZY)
    private Class<Signifier<String, ResignifiedJpa>> names;

    /**
     * Constructor.
     */
    public OrganisationJpa(final String identifier, final Class<MembershipJpa<R>> members, final String purpose, final Class<OrganisationJpa<R>> units,
        final Class<Signifier<String, ResignifiedJpa>> names, final FormedJpa beginning, final DissolvedJpa ending) {
        super(identifier, beginning, ending);
        this.members = members;
        this.purpose = purpose;
        this.units = units;
        this.names = names;
    }
}