package pks.repository.impl;

import pks.model.Patient;
import pks.model.PatientRecord;
import pks.repository.PatientRepository;

import java.util.*;
import java.util.stream.Stream;

public class PatientRepositoryImpl implements PatientRepository {

    private final Map<Integer, Patient> patients;

    private PatientRepositoryImpl(final Map<Integer, Patient> patients) {
        this.patients = patients;
    }

    @Override
    public Stream<Patient> patients() {
        return patients.values().stream();
    }

    public static class Builder {

        private final Map<Integer, Patient> patients = new HashMap<>();

        public Builder withRecord(final PatientRecord record) {
            final int patientId = record.getPatientId();
            final Patient patient = Optional
                    .ofNullable(patients.get(patientId))
                    .orElseGet(() -> {
                        final Patient newPatient = new Patient(patientId);
                        patients.put(patientId, newPatient);
                        return newPatient;
                    });

            patient.addRecord(record);
            return this;
        }

        public PatientRepository build() {
            return new PatientRepositoryImpl(patients);
        }
    }
}
