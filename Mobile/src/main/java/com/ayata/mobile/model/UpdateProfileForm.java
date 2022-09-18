package com.ayata.mobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileForm implements Serializable {
    private String chw_id;
    private String chw_name;
    private String chw_address;
    private String chw_dob;
    private String chw_designation;
    private String chw_gender;
    private MultipartFile image;
}
