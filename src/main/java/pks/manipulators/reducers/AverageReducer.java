package pks.manipulators.reducers;

import java.util.stream.Stream;

/*
 * Average value calculator.
 */
public class AverageReducer {
    public static double reduce(final Stream<String> stream) {
        // parsing to double on the fly
        // can be done on startup if we use Variant
        return stream.mapToDouble(Double::parseDouble).average().orElse(0.0);
    }
}
