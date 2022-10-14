package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CLab{
    private int _visit_patient_detail_ward_ward_id;
    private int chronic_lab_id;
    private String dbp;
    private String oxygen;
    private String patient_id;
    private String sbp;
    private String sugar;
    private String visit_id;
}
