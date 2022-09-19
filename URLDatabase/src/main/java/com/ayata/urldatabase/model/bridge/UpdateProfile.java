package com.ayata.urldatabase.model.bridge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfile {
    private Integer chw_id;
    private String chw_name;
    private String chw_address;
    private String chw_dob;
    private String chw_designation;
    private String chw_gender;
    private String image;
}
