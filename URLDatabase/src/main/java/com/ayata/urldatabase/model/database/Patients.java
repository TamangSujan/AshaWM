package com.ayata.urldatabase.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "patients")
public class Patients{
    @Id @JsonIgnore
    private String _id;
    @JsonIgnore
    private String user;

    private Date patientAddedDate;
    @JsonIgnore
    private String patientAge;
    @JsonIgnore
    private String patientDob;
    @JsonIgnore
    private String patientFirstName;

    private String patientFullName;
    @JsonIgnore
    private String patientGender;
    @JsonIgnore
    private String patientHouseno;

    private String patientId;
    @JsonIgnore
    private String patientLastName;

    private String patientMunicipality;

    private String patientPhone;
    @JsonIgnore
    private String patientSpouseFullName;
    @JsonIgnore
    private String patientVillagename;
    @JsonIgnore
    private String patientspousefirstname;
    @JsonIgnore
    private String patientspouselastname;
    @JsonIgnore
    private String patientwardno;

    private String image;
    @JsonIgnore
    private String __v;
    @JsonIgnore
    private boolean deleted;
}
