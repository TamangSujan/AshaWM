package com.ayata.urldatabase.model.bridge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(value = "users")
public class WebAddStaffForm {
    @JsonIgnore
    private String _id;
    private Integer chw_age;
    private Integer chw_id;
    private Integer chw_identifier;
    private String creator;
    private String chw_notes;
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
    private MultipartFile image;
    private MultipartFile file;
}
