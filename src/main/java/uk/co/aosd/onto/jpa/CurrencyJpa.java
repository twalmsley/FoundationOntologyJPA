package uk.co.aosd.onto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.money.Currency;

/**
 * A record of a currency.
 *
 * @author Tony Walmsley
 */
@Entity(name = "CURRENCY")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CurrencyJpa extends UniquelyIdentifiableJpa implements Currency {

    @Column(name = "ABBREVIATION", columnDefinition = "VARCHAR(3)")
    private String abbreviation;

    @Column(name = "NAME", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "SYMBOL", columnDefinition = "CHAR(1)")
    private char symbol;

    /**
     * Constructor.
     */
    public CurrencyJpa(final String identifier, final String abbreviation, final String name, final char symbol) {
        super(identifier);
        this.abbreviation = abbreviation;
        this.name = name;
        this.symbol = symbol;
    }
}
