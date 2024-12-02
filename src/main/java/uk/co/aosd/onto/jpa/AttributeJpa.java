package uk.co.aosd.onto.jpa;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeJpa<I extends Individual<? extends Event, ? extends Event>, P extends Serializable> implements Attribute<I, P> {
    @Id
    private String identifier;

    @ManyToOne(targetEntity = IndividualJpa.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private I individual;

    @Convert(converter = SerializableConverter.class)
    @Target(Serializable.class)
    private P property;

    @Column(name = "beginning")
    private Instant from;

    @Column(name = "ending")
    private Instant to;

}
