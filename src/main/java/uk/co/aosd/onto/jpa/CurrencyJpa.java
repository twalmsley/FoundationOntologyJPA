package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.money.Currency;

/**
 * A record of a currency.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyJpa implements Currency {

    private String identifier;
    private String abbreviation;
    private String name;
    private char symbol;

}
