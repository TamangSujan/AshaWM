package com.ayata.urldatabase.model.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelVisitList{
    private EnteredDateTime entered_date_time;
    private ModelVisitSafe modelVisitSafe;
    private String patient_id;
    private String visit_category;
    private String visit_followupdate_english;
    private String visit_followupdate_nepali;
    private String visit_id;
    private String visit_lastdate_english;
    private String visit_lastdate_nepali;
    private double visit_latitude;
    private double visit_longitude;
    private String ward;
    private boolean isSync;
    private ModelVisitChronic modelVisitChronic;
}
