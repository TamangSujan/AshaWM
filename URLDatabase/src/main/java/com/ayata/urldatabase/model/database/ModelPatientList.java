package com.ayata.urldatabase.model.database;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelPatientList{
    private double latitude;
    private double longitude;
    private Object modelVisitList;
    private String patientAddedDate;
    private String patientAge;
    private String patientDob;
    private String patientFirstName;
    private String patientFullName;
    private String patientGender;
    private String patientHouseno;
    private String patientId;
    private String patientLastName;
    private String patientMunicipality;
    private String patientPhone;
    private String patientSpouseFullName;
    private String patientVillagename;
    private String patientspousefirstname;
    private String patientspouselastname;
    private int patientwardno;
}
