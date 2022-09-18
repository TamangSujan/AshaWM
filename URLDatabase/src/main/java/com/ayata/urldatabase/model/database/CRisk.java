package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CRisk{
    private int _visit_patient_detail_ward_ward_id;
    private int chronic_risk_id;
    private String patient_id;
    private ArrayList<String> riskName;
    private String visit_id;
}
