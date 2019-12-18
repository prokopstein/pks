package pks.model;

import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

public class Patient {
    @Getter
    private final int patientId;

    // map: attribute name => (map: date => attribute value)
    //
    // map: date => attribute value is a SortedMap since we want chronological order of events
    //
    // attribute value of type String can be replaced with something like Variant which can hold different types
    // so the conversions like String => Double would have been done once on creation
    private Map<String, SortedMap<Integer, String>> attributes = new HashMap<>();

    public Patient(final int patientId) {
        this.patientId = patientId;
    }

    // streaming all records of specified attribute
    public Stream<String> attribute(final String attributeName) {
        return Optional
                .ofNullable(attributes.get(attributeName))
                .map(Map::values)
                .map(Collection::stream)
                .orElse(Stream.empty());
    }

    // returning last value of an attribute - convenient method instead of iterating whole collection
    public Optional<String> last(final String attributeName) {
        return Optional.ofNullable(attributes.get(attributeName)).map(map -> {
            try {
                final Integer lastKey = map.lastKey();
                return map.get(lastKey);
            } catch (final NoSuchElementException e) {
                return null;
            }
        });
    }

    // adding a record
    public void addRecord(final PatientRecord patientRecord) {
        Optional.ofNullable(attributes.get(patientRecord.getAttributeName()))
                .orElseGet(() -> {
                    final SortedMap<Integer, String> map = new TreeMap<>();
                    attributes.put(patientRecord.getAttributeName(), map);
                    return map;
                })
                .put(patientRecord.getRecordDate(), patientRecord.getAttributeValue());
    }
}
