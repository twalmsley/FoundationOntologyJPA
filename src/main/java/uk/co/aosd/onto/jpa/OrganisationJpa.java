package uk.co.aosd.onto.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Dissolved;
import uk.co.aosd.onto.events.Formed;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.jpa.events.DissolvedJpa;
import uk.co.aosd.onto.jpa.events.FormedJpa;
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
public class OrganisationJpa<R extends Role> extends UniquelyIdentifiableJpa implements Organisation {
    @OneToOne(targetEntity = ClassJpa.class, cascade = { CascadeType.ALL })
    private Class<Membership<R>> members;

    @Column(name = "PURPOSE", nullable = false, updatable = true, columnDefinition = "LONGTEXT")
    private String purpose;

    @OneToOne(targetEntity = ClassJpa.class, cascade = { CascadeType.ALL })
    private Class<Organisation> units;

    @OneToOne(targetEntity = ClassJpa.class, cascade = { CascadeType.ALL })
    private Class<Signifier<String>> names;

    @OneToOne(targetEntity = FormedJpa.class, cascade = { CascadeType.ALL })
    private Formed beginning;

    @OneToOne(targetEntity = DissolvedJpa.class, cascade = { CascadeType.ALL })
    private Dissolved ending;

    /**
     * Constructor.
     */
    public OrganisationJpa(final String identifier, final Class<Membership<R>> members, final String purpose, final Class<Organisation> units,
        final Class<Signifier<String>> names, final Formed beginning, final Dissolved ending) {
        super(identifier);
        this.members = members;
        this.purpose = purpose;
        this.units = units;
        this.names = names;
        this.beginning = beginning;
        this.ending = ending;
    }
}