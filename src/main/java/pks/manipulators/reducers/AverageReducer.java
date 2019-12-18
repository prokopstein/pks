package pks.manipulators.reducers;

import java.util.stream.Stream;

public class AverageReducer {
    public static double reduce(final Stream<String> stream) {
        // TODO:
        return stream.mapToDouble(Double::parseDouble).average().orElse(0.0);
    }
}
