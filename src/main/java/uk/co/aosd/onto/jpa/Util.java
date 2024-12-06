package uk.co.aosd.onto.jpa;

import java.util.UUID;

import lombok.experimental.UtilityClass;

/**
 * Utility functions.
 *
 * @author Tony Walmsley
 */
@UtilityClass
public class Util {

    public static String randId() {
        return UUID.randomUUID().toString();
    }
}
