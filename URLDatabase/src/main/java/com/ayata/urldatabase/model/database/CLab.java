package com.ayata.urldatabase.model.database;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
