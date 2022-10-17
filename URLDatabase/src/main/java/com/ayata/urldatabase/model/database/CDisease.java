package com.ayata.urldatabase.model.database;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CDisease{
    private int _visit_patient_detail_ward_ward_id;
    private int chronic_disease_id;
    private ArrayList<String> diseaseName;
    private String patient_id;
    private String visit_id;
}
