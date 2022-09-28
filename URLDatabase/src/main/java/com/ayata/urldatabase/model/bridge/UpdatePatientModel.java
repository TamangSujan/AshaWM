package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.database.Patients;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePatientModel {
    private String creator;
    private String gender;
    private String image;
    private boolean latitude;
    private boolean longitude;
    private String patientAddedDate;
    private String patientAge;
    private String patientDob;
    private String patientFirstName;
    private String patientFullName;
    private String patientHouseno;
    private String patientLastName;
    private String patientMiddleName;
    private String patientMunicipality;
    private String patientPhone;
    private String patientSpouseFullName;
    private String patientVillagename;
    private String patientspousefirstname;
    private String patientspouselastname;
    private Integer patientwardno;
    private String pid;

    public Patients getChangedPatient(Patients patient){
        patient.setLatitude(this.latitude);
        patient.setLongitude(this.longitude);
        patient.setPatientAddedDate(this.patientAddedDate);
        patient.setPatientAge(this.patientAge);
        patient.setPatientFirstName(this.patientFirstName);
        patient.setPatientHouseno(this.patientHouseno);
        patient.setPatientLastName(this.patientLastName);
        patient.setPatientFullName(this.patientFullName);
        patient.setPatientMunicipality(this.patientMunicipality);
        patient.setPatientPhone(this.patientPhone);
        patient.setPatientSpouseFullName(this.patientSpouseFullName);
        patient.setPatientVillagename(this.patientVillagename);
        patient.setPatientwardno(this.patientwardno);
        patient.setPatientId(this.pid);
        return patient;
    }
}
