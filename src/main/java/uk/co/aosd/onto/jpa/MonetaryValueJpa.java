package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.decimal4j.immutable.Decimal3f;
import uk.co.aosd.onto.money.Currency;
import uk.co.aosd.onto.money.MonetaryValue;

/**
 * An implementation of the MonetaryValue interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonetaryValueJpa<U extends Currency> implements MonetaryValue<U> {
    private Decimal3f value;
    private U unit;
}
