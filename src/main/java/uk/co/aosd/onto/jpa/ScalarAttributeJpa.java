package uk.co.aosd.onto.jpa;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Target;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.ScalarAttribute;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.Unit;
import uk.co.aosd.onto.jpa.converters.ScalarValueConverter;

/**
 * An implementation of the ScalarAttribute interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "SCALAR_ATTRIBUTE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScalarAttributeJpa<I extends Individual<? extends Event, ? extends Event>, N extends Number, U extends Unit> extends UniquelyIdentifiableJpa
    implements ScalarAttribute<I, N, U> {

    @ManyToOne(targetEntity = IndividualJpa.class)
    private I individual;

    @Convert(converter = ScalarValueConverter.class)
    @Target(ScalarValueJpa.class)
    @Column(name = "PROPERTY", columnDefinition = "VARCHAR(255)")
    private ScalarValue<N, U> property;

    @Column(name = "BEGINNING")
    private Instant from;

    @Column(name = "ENDING")
    private Instant to;

    /**
     * Constructor.
     */
    public ScalarAttributeJpa(final String identifier, final I individual, final ScalarValue<N, U> property, final Instant from, final Instant to) {
        super(identifier);
        this.individual = individual;
        this.property = property;
        this.from = from;
        this.to = to;
    }
}
