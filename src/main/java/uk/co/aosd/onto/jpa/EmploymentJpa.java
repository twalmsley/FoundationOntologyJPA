package uk.co.aosd.onto.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Appointed;
import uk.co.aosd.onto.events.Removed;
import uk.co.aosd.onto.jpa.events.AppointedJpa;
import uk.co.aosd.onto.jpa.events.RemovedJpa;
import uk.co.aosd.onto.organisation.Employment;
import uk.co.aosd.onto.organisation.Organisation;

/**
 * An implementation of the Employment interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmploymentJpa extends UniquelyIdentifiableJpa implements Employment<String> {

    @ManyToOne(targetEntity = OrganisationJpa.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Organisation employer;

    @ManyToOne(targetEntity = HumanJpa.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Human employee;

    @Column(name = "description")
    private String actionsDescription;

    private String contract;

    @OneToOne(targetEntity = AppointedJpa.class, cascade = { CascadeType.ALL })
    private Appointed beginning;

    @OneToOne(targetEntity = RemovedJpa.class, cascade = { CascadeType.ALL })
    private Removed ending;

    /**
     * Constructor.
     */
    public EmploymentJpa(final String identifier, final Organisation employer, final Human employee, final String actionsDescription, final String contract,
        final Appointed beginning, final Removed ending) {
        super(identifier);
        this.employer = employer;
        this.employee = employee;
        this.actionsDescription = actionsDescription;
        this.contract = contract;
        this.beginning = beginning;
        this.ending = ending;
    }
}
