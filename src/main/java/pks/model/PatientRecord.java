package pks.model;

import lombok.Data;

@Data
public class PatientRecord {
    private int patientId;
    // TODO: to int
    private String date;
    private String attributeName;
    //TODO: to value
    private String attributeValue;
}
