package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CRisk{
    private String _visit_patient_detail_ward_ward_id;
    private String chronic_risk_id;
    private String patient_id;
    private ArrayList<String> riskName;
    private String visit_id;
}
