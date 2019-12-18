package pks.manipulators.filters;

import lombok.AllArgsConstructor;
import pks.model.Patient;

@AllArgsConstructor
public class AttributeEqualFilter {
    private final String attributeName;
    private final String attributeValue;

    public boolean filter(final Patient patient) {
        return patient
                .last(attributeName)
                .map(attributeValue::equals)
                .orElse(false);
    }
}
