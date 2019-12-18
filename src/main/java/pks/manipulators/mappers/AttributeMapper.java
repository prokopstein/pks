package pks.manipulators.mappers;

import lombok.AllArgsConstructor;
import pks.model.Patient;

@AllArgsConstructor
public class AttributeMapper {
    private final String attributeName;

    // TODO:
    public String map(final Patient patient) {
        return patient.last(attributeName).orElse(null);
    }
}
