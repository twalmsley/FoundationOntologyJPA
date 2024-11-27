package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Aggregated;
import uk.co.aosd.onto.events.Disaggregated;
import uk.co.aosd.onto.foundation.Aggregate;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.Unit;

/**
 * An implementation of the Aggregation interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregateJpa<N extends Number, U extends Unit, T> implements Aggregate<N, U, T> {
    private String identifier;
    private Class<T> kind;
    private ScalarValue<N, U> quantity;
    private Aggregated beginning;
    private Disaggregated ending;

}
