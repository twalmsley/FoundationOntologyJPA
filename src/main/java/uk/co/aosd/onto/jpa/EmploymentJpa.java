package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.jpa.events.AppointedJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.DissolvedJpa;
import uk.co.aosd.onto.jpa.events.FormedJpa;
import uk.co.aosd.onto.jpa.events.RemovedJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
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
public class EmploymentJpa extends IndividualJpa<AppointedJpa, RemovedJpa>
    implements Employment<String, AppointedJpa, RemovedJpa, FormedJpa, DissolvedJpa, ResignifiedJpa, BirthJpa, DeathJpa, LanguageJpa> {

    @ManyToOne(targetEntity = OrganisationJpa.class, fetch = FetchType.LAZY)
    private Organisation<FormedJpa, DissolvedJpa, ResignifiedJpa> employer;

    @ManyToOne(targetEntity = HumanJpa.class, fetch = FetchType.LAZY)
    private Human<BirthJpa, DeathJpa, ResignifiedJpa, LanguageJpa> employee;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String actionsDescription;

    @Column(name = "CONTRACT", columnDefinition = "TEXT")
    private String contract;

    /**
     * Constructor.
     */
    public EmploymentJpa(final String identifier, final Organisation<FormedJpa, DissolvedJpa, ResignifiedJpa> employer,
        final Human<BirthJpa, DeathJpa, ResignifiedJpa, LanguageJpa> employee, final String actionsDescription, final String contract,
        final AppointedJpa beginning,
        final RemovedJpa ending) {
        super(identifier, beginning, ending);
        this.employer = employer;
        this.employee = employee;
        this.actionsDescription = actionsDescription;
        this.contract = contract;
    }
}
