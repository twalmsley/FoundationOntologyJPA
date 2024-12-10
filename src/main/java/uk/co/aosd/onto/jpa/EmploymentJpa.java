package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Appointed;
import uk.co.aosd.onto.events.Removed;
import uk.co.aosd.onto.organisation.Employment;
import uk.co.aosd.onto.organisation.Organisation;

/**
 * An implementation of the Employment interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "EMPLOYMENT")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmploymentJpa extends IndividualJpa<Appointed, Removed> implements Employment<String> {

    @ManyToOne(targetEntity = OrganisationJpa.class)
    private Organisation employer;

    @ManyToOne(targetEntity = HumanJpa.class)
    private Human employee;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String actionsDescription;

    @Column(name = "CONTRACT", columnDefinition = "TEXT")
    private String contract;

    /**
     * Constructor.
     */
    public EmploymentJpa(final String identifier, final Organisation employer, final Human employee, final String actionsDescription, final String contract,
        final Appointed beginning, final Removed ending) {
        super(identifier, beginning, ending);
        this.employer = employer;
        this.employee = employee;
        this.actionsDescription = actionsDescription;
        this.contract = contract;
    }
}
