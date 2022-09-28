package com.ayata.urldatabase.model.bridge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientShortDetails {
    private String patientId;
    private String image;
    private String patientVillagename;
    private String patientMunicipality;
    private String patientAddedDate;
    private String patientPhone;
    private String patientFullName;
}
