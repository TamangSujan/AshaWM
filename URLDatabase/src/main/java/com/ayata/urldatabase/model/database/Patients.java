package com.ayata.urldatabase.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Patients{
    @Id @JsonIgnore
    private String _id;
    private String user;
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
    private Integer patientwardno;
    private boolean longitude;
    private boolean latitude;
    private String image;
    private String __v;
    private boolean deleted;
}
