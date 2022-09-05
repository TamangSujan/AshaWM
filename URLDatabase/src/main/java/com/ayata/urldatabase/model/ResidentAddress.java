package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentAddress{
    private String resident_city;
    private String resident_district;
    private String resident_province;
    private String resident_village;
    private String resident_ward;
}
