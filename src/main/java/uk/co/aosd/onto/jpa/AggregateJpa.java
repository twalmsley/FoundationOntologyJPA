package uk.co.aosd.onto.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Target;
import uk.co.aosd.onto.events.Aggregated;
import uk.co.aosd.onto.events.Disaggregated;
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
@AllArgsConstructor
@NoArgsConstructor
public class AggregateJpa<N extends Number, U extends Unit, T> implements Aggregate<N, U, T> {
    @Id
    @Column(name = "IDENTIFIER")
    private String identifier;

    @Column(name = "KIND", nullable = false, length = 255, unique = true, updatable = false, columnDefinition = "VARCHAR(255)")
    @Convert(converter = ClassConverter.class)
    @Target(Class.class)
    private Class<T> kind;

    @Column(name = "QUANTITY", nullable = false, updatable = false, columnDefinition = "VARCHAR(255)")
    @Convert(converter = ScalarValueConverter.class)
    @Target(ScalarValue.class)
    private ScalarValue<N, U> quantity;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = AggregatedJpa.class)
    private Aggregated beginning;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = DisaggregatedJpa.class)
    private Disaggregated ending;

}
