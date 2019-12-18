package pks.queries;

import pks.manipulators.filters.AttributeEqualFilter;
import pks.manipulators.filters.AttributeRangeFilter;
import pks.manipulators.mappers.AllAttributeMapper;
import pks.manipulators.mappers.AttributeMapper;
import pks.manipulators.reducers.AverageReducer;
import pks.repository.PatientRepository;

import java.util.*;
import java.util.stream.Collectors;

public class Queries {

    private final AttributeEqualFilter maleFilter = new AttributeEqualFilter("Gender", "M");
    private final AttributeEqualFilter femaleFilter = new AttributeEqualFilter("Gender", "F");

    private final AttributeMapper ageMapper = new AttributeMapper("Age");
    private final AllAttributeMapper bloodMapper = new AllAttributeMapper("Blood pressure");
    private final AllAttributeMapper glucoseMapper = new AllAttributeMapper("Glucose");

    public long maleCount(final PatientRepository patientRepository) {
        long s = patientRepository
                .patients()
                .filter(maleFilter::filter)
                .count();

        System.out.println(String.format("Male count: %d", s));
        return s;
    }

    public long femaleCount(final PatientRepository patientRepository) {
        long s = patientRepository
                .patients()
                .filter(femaleFilter::filter)
                .count();

        System.out.println(String.format("Female count: %d", s));
        return s;
    }

    public double maleAge(final PatientRepository patientRepository) {
        double s = AverageReducer.reduce(patientRepository
                .patients()
                .filter(maleFilter::filter)
                .map(ageMapper::map)
                .filter(Objects::nonNull));

        System.out.println(String.format("Average male age: %.2f", s));
        return s;
    }

    public double femaleAge(final PatientRepository patientRepository) {
        double s = AverageReducer.reduce(patientRepository
                .patients()
                .filter(femaleFilter::filter)
                .map(ageMapper::map)
                .filter(Objects::nonNull));

        System.out.println(String.format("Average female age: %.2f", s));
        return s;
    }

    public List<Integer> increasingGlucoseLevel(final PatientRepository patientRepository) {
        return patientRepository
                .patients()
                .map(patient -> {
                    final List<Double> levels = glucoseMapper
                            .map(patient)
                            .map(Double::parseDouble)
                            .collect(Collectors.toList());

                    // something more sophisticated required :)
                    double s = 0.0;
                    for (int i = 1; i < levels.size(); ++i) {
                        s += levels.get(i) - levels.get(i - 1);
                    }

                    if (s > 0.5) {
                        System.out.println(String.format("Patient #%d has increased glucose level", patient.getPatientId()));
                        return patient.getPatientId();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Map<Integer, Double> bloodPressureReport(final PatientRepository patientRepository) {
        final AttributeRangeFilter ageRangeFilter = new AttributeRangeFilter("Age", "23", "26");

        final Map<Integer, Double> patients = new HashMap<>();
        patientRepository
                .patients()
                .filter(ageRangeFilter::filter)
                .forEach(patient -> {
                    double s = AverageReducer.reduce(bloodMapper.map(patient));
                    System.out.println(String.format("Patient #%d average blood pressure: %.2f", patient.getPatientId(), s));

                    patients.put(patient.getPatientId(), s);
                });

        return patients;
    }
}
