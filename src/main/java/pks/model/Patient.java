package pks.model;

import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

public class Patient {
    @Getter
    private final int patientId;

    private Map<String, SortedMap<String, String>> attributes = new HashMap<>();

    public Patient(final int patientId) {
        this.patientId = patientId;
    }

    public Stream<String> attribute(final String attributeName) {
        return Optional
                .ofNullable(attributes.get(attributeName))
                .map(Map::values)
                .map(Collection::stream)
                .orElse(Stream.empty());
    }

    public Optional<String> last(final String attributeName) {
        return Optional.ofNullable(attributes.get(attributeName)).map(map -> {
            try {
                final String lastKey = map.lastKey();
                return map.get(lastKey);
            } catch (final NoSuchElementException e) {
                return null;
            }
        });
    }

    // TODO:
    public void addAttributeRecord(final String attributeName, final String recordDate, final String attributeValue) {
        Optional.ofNullable(attributes.get(attributeName))
                .orElseGet(() -> {
                    final SortedMap<String, String> map = new TreeMap<>();
                    attributes.put(attributeName, map);
                    return map;
                })
                .put(recordDate, attributeValue);
    }
}
