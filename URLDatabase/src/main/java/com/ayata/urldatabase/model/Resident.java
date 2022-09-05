package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident{
    private Double latitude;
    private Double longitude;
    private String resident_first_name;
    private String resident_last_name;
    private String resident_middle_name;
}
