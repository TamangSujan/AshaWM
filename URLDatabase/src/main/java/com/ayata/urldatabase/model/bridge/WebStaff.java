package com.ayata.urldatabase.model.bridge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(value = "users")
public class WebStaff {
    @JsonIgnore
    private String _id;
    private Integer chw_id;
    private Integer chw_age;
    private Integer chw_identifier;
    private String creator;
    private String chw_notes;
    private String chw_name;
    private String chw_firstname;
    private String chw_middlename;
    private String chw_lastname;
    private String chw_firstname_nepali;
    private String chw_middlename_nepali;
    private String chw_lastname_nepali;
    private String chw_dob;
    private String chw_doc_type;
    private Integer chw_doc_number;
    private String chw_gender;
    private String chw_contact;
    private String chw_alt_contact;
    private String phone;
    private String password;
    private String chw__address_country;
    private String chw_address_province;
    private String chw_address_district;
    private String chw_address_municipality;
    private Integer chw_address_ward;
    private String chw_edu_undergraduate;
    private String chw_edu_postgraduate;
    private String chw_emergency_contact_firstname;
    private String chw_emergency_contact_middlename;
    private String chw_emergency_contact_lastname;
    private String chw_emergency_contact_relation;
    private String chw_emergency_contact_number;
    private String chw_dob_nepali;
    private String image;
    private String file;

    /*
        Password is not set because it should be encrypted.
        Image and File is not set as WebAddStaffForm has files but this class has string
     */
    public static WebStaff getWebStaff(WebAddStaffForm webAddStaffForm){
        WebStaff webStaff = new WebStaff();
        webStaff.chw_age = webAddStaffForm.getChw_age();
        webStaff.chw_identifier = webAddStaffForm.getChw_identifier();
        webStaff.creator = webAddStaffForm.getCreator();
        webStaff.chw_notes = webAddStaffForm.getChw_notes();
        webStaff.chw_firstname = webAddStaffForm.getChw_firstname();
        webStaff.chw_middlename = webAddStaffForm.getChw_middlename();
        webStaff.chw_lastname = webAddStaffForm.getChw_lastname();
        webStaff.chw_name = webStaff.chw_firstname +" "+ webStaff.chw_middlename +" "+ webStaff.chw_lastname;
        webStaff.chw_firstname_nepali = webAddStaffForm.getChw_firstname_nepali();
        webStaff.chw_middlename_nepali = webAddStaffForm.getChw_middlename_nepali();
        webStaff.chw_lastname_nepali = webAddStaffForm.getChw_lastname_nepali();
        webStaff.chw_dob = webAddStaffForm.getChw_dob();
        webStaff.chw_doc_type = webAddStaffForm.getChw_doc_type();
        webStaff.chw_doc_number = webAddStaffForm.getChw_doc_number();
        webStaff.chw_gender = webAddStaffForm.getChw_gender();
        webStaff.chw_contact = webAddStaffForm.getChw_contact();
        webStaff.chw_alt_contact = webAddStaffForm.getChw_alt_contact();
        webStaff.phone = webAddStaffForm.getPhone();
        webStaff.chw__address_country = webAddStaffForm.getChw__address_country();
        webStaff.chw_address_province = webAddStaffForm.getChw_address_province();
        webStaff.chw_address_district = webAddStaffForm.getChw_address_district();
        webStaff.chw_address_municipality = webAddStaffForm.getChw_address_municipality();
        webStaff.chw_address_ward = webAddStaffForm.getChw_address_ward();
        webStaff.chw_edu_undergraduate = webAddStaffForm.getChw_edu_undergraduate();
        webStaff.chw_edu_postgraduate = webAddStaffForm.getChw_edu_postgraduate();
        webStaff.chw_emergency_contact_firstname = webAddStaffForm.getChw_emergency_contact_firstname();
        webStaff.chw_emergency_contact_middlename = webAddStaffForm.getChw_emergency_contact_middlename();
        webStaff.chw_emergency_contact_lastname = webAddStaffForm.getChw_emergency_contact_lastname();
        webStaff.chw_emergency_contact_relation = webAddStaffForm.getChw_emergency_contact_relation();
        webStaff.chw_emergency_contact_number = webAddStaffForm.getChw_emergency_contact_number();
        webStaff.chw_dob_nepali = webAddStaffForm.getChw_dob_nepali();
        return webStaff;
    }
}
