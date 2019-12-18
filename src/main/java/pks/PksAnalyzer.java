package pks;

import pks.exceptions.ReadDataException;
import pks.model.PatientRecord;
import pks.queries.Queries;
import pks.reader.DataReader;
import pks.reader.csv.CsvDataReader;
import pks.repository.PatientRepository;
import pks.repository.impl.PatientRepositoryImpl;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class PksAnalyzer {

    private static final Logger logger = Logger.getLogger(PksAnalyzer.class.getName());

    public static void main(String[] args) {
        if (args.length < 1) {
            logger.info("App requires at least one command line argument");
            return;
        }

        final PatientRepository patientRepository = buildRepository(args[0]);

        // run all queries in parallel
        final Queries queries = new Queries();
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> queries.maleCount(patientRepository)),
                CompletableFuture.runAsync(() -> queries.femaleCount(patientRepository)),
                CompletableFuture.runAsync(() -> queries.maleAge(patientRepository)),
                CompletableFuture.runAsync(() -> queries.femaleAge(patientRepository)),
                CompletableFuture.runAsync(() -> queries.increasingGlucoseLevel(patientRepository)),
                CompletableFuture.runAsync(() -> queries.bloodPressureReport(patientRepository))
        ).join();
    }

    public static PatientRepository buildRepository(final String filePath) {
        final DataReader reader = new CsvDataReader(filePath);

        try (final Stream<PatientRecord> records = reader.read()) {

            final PatientRepositoryImpl.Builder builder = new PatientRepositoryImpl.Builder();
            records.forEach(builder::withRecord);

            return builder.build();
        } catch (final ReadDataException ex) {
            logger.severe(ex.toString());
            throw ex;
        }
    }
}
