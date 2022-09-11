package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "users")
public class Users{
    @Id
    private String _id;
    private Integer chw_id;
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
