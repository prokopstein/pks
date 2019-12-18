package pks.manipulators.filters;

import lombok.AllArgsConstructor;
import pks.model.Patient;

/*
 * Filtering last value of specified attribute to be within specified range.
 */
@AllArgsConstructor
public class AttributeRangeFilter {
    private final String attributeName;
    private final String minValue;
    private final String maxValue;

    public boolean filter(final Patient patient) {
        return patient
                .last(attributeName)
                .map(value -> minValue.compareTo(value) <= 0 && maxValue.compareTo(value) >= 0)
                .orElse(false);
    }
}
