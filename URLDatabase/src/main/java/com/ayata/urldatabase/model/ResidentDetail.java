package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidentDetail{
    private Integer resident_age;
    private String resident_gender;
    private String resident_is_resident_living_there;
    private String resident_marital_status;
}
