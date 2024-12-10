package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Dissolved;
import uk.co.aosd.onto.events.Formed;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.organisation.Membership;
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
public class OrganisationJpa<R extends Role> extends IndividualJpa<Formed, Dissolved> implements Organisation {
    @OneToOne(targetEntity = ClassJpa.class)
    private Class<Membership<R>> members;

    @Column(name = "PURPOSE", columnDefinition = "TEXT")
    private String purpose;

    @OneToOne(targetEntity = ClassJpa.class, fetch = FetchType.LAZY)
    private Class<Organisation> units;

    @OneToOne(targetEntity = ClassJpa.class, fetch = FetchType.LAZY)
    private Class<Signifier<String>> names;

    /**
     * Constructor.
     */
    public OrganisationJpa(final String identifier, final Class<Membership<R>> members, final String purpose, final Class<Organisation> units,
        final Class<Signifier<String>> names, final Formed beginning, final Dissolved ending) {
        super(identifier, beginning, ending);
        this.members = members;
        this.purpose = purpose;
        this.units = units;
        this.names = names;
    }
}