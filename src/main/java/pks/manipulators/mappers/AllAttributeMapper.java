package pks.manipulators.mappers;

import lombok.AllArgsConstructor;
import pks.model.Patient;

import java.util.stream.Stream;

/*
 * Mapping to the stream of specified attribute values.
 */
@AllArgsConstructor
public class AllAttributeMapper {
    private final String attributeName;

    public Stream<String> map(final Patient patient) {
        return patient.attribute(attributeName);
    }
}
