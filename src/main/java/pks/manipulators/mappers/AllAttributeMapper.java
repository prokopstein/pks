package pks.manipulators.mappers;

import lombok.AllArgsConstructor;
import pks.model.Patient;

import java.util.stream.Stream;

@AllArgsConstructor
public class AllAttributeMapper {
    private final String attributeName;

    public Stream<String> map(final Patient patient) {
        return patient.attribute(attributeName);
    }
}
