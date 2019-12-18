package pks.repository;

import pks.model.Patient;

import java.util.stream.Stream;

public interface PatientRepository {
    Stream<Patient> patients();
}
