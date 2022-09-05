package com.ayata.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private String _id;
    private String chw_id;
    private String phone;
    private String password;
    private String chw_address;
    private String chw_name;
    private String image;
    private String chw_dob;
    private String chw_gender;
    private String chw_designation;
    private String __v;
    private boolean deleted;
}
