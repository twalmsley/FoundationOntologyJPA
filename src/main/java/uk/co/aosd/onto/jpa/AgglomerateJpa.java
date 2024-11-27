package uk.co.aosd.onto.jpa;

import java.util.Set;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.events.Aggregated;
import uk.co.aosd.onto.events.Disaggregated;
import uk.co.aosd.onto.foundation.Agglomerate;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;

/**
 * An implementation of the Agglomerate interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgglomerateJpa implements Agglomerate {

    private String identifier;

    private Set<Individual<? extends Event, ? extends Event>> parts;

    private Aggregated beginning;

    private Disaggregated ending;

}
