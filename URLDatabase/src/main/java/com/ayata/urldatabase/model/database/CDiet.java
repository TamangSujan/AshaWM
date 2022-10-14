package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CDiet{
    private int _visit_patient_detail_ward_ward_id;
    private int chronic_diet_id;
    private String green_veg;
    private String less_salt;
    private String patient_id;
    private String reduced_oil;
    private String visit_id;
}
