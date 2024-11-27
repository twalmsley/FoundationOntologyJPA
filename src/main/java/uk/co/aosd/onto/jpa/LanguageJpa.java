package uk.co.aosd.onto.jpa;

import uk.co.aosd.onto.language.Language;

/**
 * An implementation of Language.
 *
 * @author Tony Walmsley
 */
public record LanguageJpa(String identifier, String name) implements Language {

}
