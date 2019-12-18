package pks.reader;

import pks.model.PatientRecord;

import java.util.stream.Stream;

/*
 * Reading patient data and streaming them as PatientRecord
 */
public interface DataReader {
    Stream<PatientRecord> read();
}
