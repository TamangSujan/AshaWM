package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patients{
    @Id
    private String _id;
    private String user;
    private Date patientAddedDate;
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
    private String patientwardno;
    private String image;
    private String __v;
    private boolean deleted;
}
