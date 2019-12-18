package pks.reader;

import pks.model.PatientRecord;

import java.util.stream.Stream;

public interface DataReader {
    Stream<PatientRecord> read();
}
