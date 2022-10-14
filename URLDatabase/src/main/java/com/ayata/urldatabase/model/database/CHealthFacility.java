package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CHealthFacility{
    private int _visit_patient_detail_ward_ward_id;
    private int chronic_health_facility_id;
    private String health_facility_last_visit_date_eng;
    private String health_facility_last_visit_date_ne;
    private String health_facility_name;
    private String patient_id;
    private String visit_id;
}
