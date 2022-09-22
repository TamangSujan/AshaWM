package com.ayata.urldatabase.model.bridge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorBasicDetails {
    private Integer doc_id;
    private String name;
    private String phone;
    private String address;
    private String bio;
}
