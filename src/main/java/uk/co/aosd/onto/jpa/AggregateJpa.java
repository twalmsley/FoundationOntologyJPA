package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Target;
import uk.co.aosd.onto.foundation.Aggregate;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.Unit;
import uk.co.aosd.onto.jpa.converters.ClassConverter;
import uk.co.aosd.onto.jpa.converters.ScalarValueConverter;
import uk.co.aosd.onto.jpa.events.AggregatedJpa;
import uk.co.aosd.onto.jpa.events.DisaggregatedJpa;

/**
 * An implementation of the Aggregation interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "AGGREGATE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AggregateJpa<N extends Number, U extends Unit, T> extends IndividualJpa<AggregatedJpa, DisaggregatedJpa>
    implements Aggregate<N, U, T, AggregatedJpa, DisaggregatedJpa> {

    @Column(name = "KIND", length = 255, columnDefinition = "VARCHAR(255)")
    @Convert(converter = ClassConverter.class)
    @Target(Class.class)
    private Class<T> kind;

    @Column(name = "QUANTITY", columnDefinition = "VARCHAR(255)")
    @Convert(converter = ScalarValueConverter.class)
    @Target(ScalarValue.class)
    private ScalarValue<N, U> quantity;

    /**
     * Constructor.
     */
    public AggregateJpa(final String identifier, final Class<T> kind, final ScalarValue<N, U> quantity, final AggregatedJpa beginning,
        final DisaggregatedJpa ending) {
        super(identifier, beginning, ending);
        this.kind = kind;
        this.quantity = quantity;
    }
}
