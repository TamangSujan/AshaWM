package com.ayata.urldatabase.model.bridge.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientCountResponse {
    private int totalCD;
    private int totalSM;
    private int totalP;
}
