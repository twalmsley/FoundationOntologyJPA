package uk.co.aosd.onto.jpa;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Target;
import uk.co.aosd.onto.foundation.Attribute;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.jpa.converters.SerializableConverter;

/**
 * An implementation of the Attribute interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "ATTRIBUTE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttributeJpa<I extends Individual<? extends Event, ? extends Event>, P extends Serializable> extends UniquelyIdentifiableJpa
    implements Attribute<I, P> {

    @ManyToOne(targetEntity = IndividualJpa.class, fetch = FetchType.LAZY)
    private I individual;

    @Convert(converter = SerializableConverter.class)
    @Target(Serializable.class)
    @Column(name = "PROPERTY", columnDefinition = "BYTEA")
    private P property;

    @Column(name = "BEGINNING", columnDefinition = "TIMESTAMP")
    private Instant from;

    @Column(name = "ENDING", columnDefinition = "TIMESTAMP")
    private Instant to;

    /**
     * Constructor.
     */
    public AttributeJpa(final String identifier, final I individual, final P property, final Instant from, final Instant to) {
        super(identifier);
        this.individual = individual;
        this.property = property;
        this.from = from;
        this.to = to;
    }
}
