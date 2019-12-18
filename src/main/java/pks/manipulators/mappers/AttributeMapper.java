package pks.manipulators.mappers;

import lombok.AllArgsConstructor;
import pks.model.Patient;

/*
 * Mapping to last value of specified attribute (null if doesn't exist).
 * Needed when we consider attributes like Age or Gender where only latest state is applicable.
 */
@AllArgsConstructor
public class AttributeMapper {
    private final String attributeName;

    public String map(final Patient patient) {
        return patient.last(attributeName).orElse(null);
    }
}
