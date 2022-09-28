package com.ayata.urldatabase.model.bridge.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private Integer doc_id;
    private String name;
    private String phone;
    private String token;
    private String ip;
}
