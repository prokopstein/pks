package pks.model;

import lombok.Data;

@Data
public class PatientRecord {
    private int patientId;
    private int recordDate;
    private String attributeName;
    private String attributeValue;
}
