package pks.reader.csv;

import pks.exceptions.ReadDataException;
import pks.model.PatientRecord;
import pks.reader.DataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CsvDataReader implements DataReader {

    private static final Logger logger = Logger.getLogger(CsvDataReader.class.getName());

    private final String filePath;

    public CsvDataReader(final String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Stream<PatientRecord> read() {
        final Path path = Paths.get(filePath);

        try {
            // read line by line => skip header => map to record => filter null records (if error)
            final Stream<String> lines = Files.lines(path);
            return lines.skip(1).map(this::toRecord).filter(Objects::nonNull);
        } catch (final IOException ex) {
            logger.severe(ex.toString());
            throw new ReadDataException(ex.getMessage(), ex.getCause());
        }
    }

    private PatientRecord toRecord(final String line) {
        try {
            // four fields per line
            final String[] fields = line.split(",");
            if (fields.length != 4) throw new Exception("Invalid record data");

            final int recordDate = toIntDate(fields[0].trim());
            final int patientId = Integer.parseInt(fields[1].trim());

            final PatientRecord record = new PatientRecord();
            record.setRecordDate(recordDate);
            record.setPatientId(patientId);
            record.setAttributeName(fields[2].trim());
            record.setAttributeValue(fields[3].trim());
            return record;
        } catch (final Exception ex) {
            logger.warning("Invalid record data. Skipping record...");
            return null;
        }
    }

    // i.e. 2019-12-18 => 20191218
    private int toIntDate(final String field) {
        final LocalDate localDate = LocalDate.parse(field);
        return localDate.getYear() * 10000 + localDate.getMonthValue() * 100 + localDate.getDayOfMonth();
    }
}
