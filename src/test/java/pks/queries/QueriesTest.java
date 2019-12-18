package pks.queries;

import org.junit.Test;
import pks.PksAnalyzer;
import pks.repository.PatientRepository;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class QueriesTest {
    private static final String filePath = "src/test/resources/messages.csv";

    private final PatientRepository patientRepository = PksAnalyzer.buildRepository(filePath);

    @Test
    public void maleCountTest() {
        final Queries queries = new Queries();
        assertEquals(1, queries.maleCount(patientRepository));
    }

    @Test
    public void femaleCountTest() {
        final Queries queries = new Queries();
        assertEquals(2, queries.femaleCount(patientRepository));
    }

    @Test
    public void maleAgeTest() {
        final Queries queries = new Queries();
        assertEquals(24.0, queries.maleAge(patientRepository), 0.01);
    }

    @Test
    public void femaleAgeTest() {
        final Queries queries = new Queries();
        assertEquals(24.5, queries.femaleAge(patientRepository), 0.01);
    }

    @Test
    public void increasingGlucoseLevelTest() {
        final Queries queries = new Queries();
        final List<Integer> patients = queries.increasingGlucoseLevel(patientRepository);

        assertNotNull(patients);
        assertEquals(1, patients.size());
        assertEquals(Integer.valueOf(1), patients.get(0));
    }

    @Test
    public void bloodPressureReportTest() {
        final Queries queries = new Queries();
        final Map<Integer, Double> patients = queries.bloodPressureReport(patientRepository);

        assertNotNull(patients);
        assertEquals(3, patients.size());
        assertEquals(Double.valueOf(170), patients.get(1));
        assertEquals(Double.valueOf(190), patients.get(2));
        assertEquals(Double.valueOf(210), patients.get(3));
    }
}
