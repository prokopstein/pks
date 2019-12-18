package pks.repository;

import pks.model.Patient;

import java.util.stream.Stream;

/*
 * Patients data storage, can stream data as Patient object
 */
public interface PatientRepository {
    Stream<Patient> patients();
}
