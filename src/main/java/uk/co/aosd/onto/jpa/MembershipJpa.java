package uk.co.aosd.onto.jpa;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Appointed;
import uk.co.aosd.onto.events.Removed;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.organisation.Membership;

/**
 * An implementation of the Membership interface.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipJpa<R extends Role> implements Membership<R> {
    String identifier;
    Human member;
    R role;
    Appointed beginning;
    Removed ending;
}