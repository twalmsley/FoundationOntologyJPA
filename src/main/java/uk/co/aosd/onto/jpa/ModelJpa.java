package uk.co.aosd.onto.jpa;

import java.util.Optional;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.model.Model;

/**
 * An implementation of the Model interface.
 *
 * @author Tony Walmsley
 */
@Entity(name = "MODEL")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModelJpa extends UniquelyIdentifiableJpa implements Model {

    @ManyToMany(targetEntity = UniquelyIdentifiableJpa.class, fetch = FetchType.LAZY)
    private Set<UniquelyIdentifiable> things;

    public Optional<UniquelyIdentifiable> getThing(final String identifier) {
        return things.stream().filter(t -> t.getIdentifier().equals(identifier)).findAny();
    }

    public void add(final UniquelyIdentifiable thing) {
        things.add(thing);
    }

    public ModelJpa(final String identifier, final Set<UniquelyIdentifiable> things) {
        super(identifier);
        this.things = things;
    }
}
