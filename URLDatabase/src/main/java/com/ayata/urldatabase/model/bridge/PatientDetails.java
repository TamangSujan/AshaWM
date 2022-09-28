package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.database.Patients;
import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.model.database.VisitLists;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDetails {
    private String patientId;
    private UsersShortDetail user;
    private String image;
    private String patientPhone;
    private String patientDob;
    private String patientAge;
    private String patientGender;
    private String patientAddedDate;
    private String patientFirstName;
    private String patientLastName;
    private String patientFullName;
    private String patientMunicipality;
    private String patientwardno;
    private String patientVillagename;
    private String patientHouseno;
    private String patientSpouseFullName;
    private String patientspousefirstname;
    private String patientspouselastname;
    private Double latitude;
    private Double longitude;
    @JsonProperty("PastVisitDates")
    private List<PastVisitDates> pastVisitDates;
    @JsonProperty("NextVisitDates")
    private List<NextVisitDates> nextVisitDates;
    private List<VisitLists> visitLists;

    public PatientDetails(Patients patient){
        this.patientAddedDate = patient.getPatientAddedDate();
        this.patientAge = patient.getPatientAge();
        this.patientDob = patient.getPatientDob();
        this.patientFirstName = patient.getPatientFirstName();
        this.patientFullName = patient.getPatientFullName();
        this.patientGender = patient.getPatientGender();
        this.patientHouseno = patient.getPatientHouseno();
        this.patientId = patient.getPatientId();
        this.patientLastName = patient.getPatientLastName();
        this.patientMunicipality = patient.getPatientMunicipality();
        this.patientPhone = patient.getPatientPhone();
        this.patientSpouseFullName = patient.getPatientSpouseFullName();
        this.patientVillagename = patient.getPatientVillagename();
        this.patientspousefirstname = patient.getPatientspousefirstname();
        this.patientspouselastname = patient.getPatientspouselastname();
        this.patientwardno = String.valueOf(patient.getPatientwardno());
        this.image = patient.getImage();
    }
}
