package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visit {
    private EnteredDateTime entered_date_time;
    private boolean isSync;
    private ModelVisitChronic modelVisitChronic;
    private ModelVisitSafe modelVisitSafe;
    private String patient_id;
    private String visit_category;
    private String visit_followupdate_english;
    private String visit_followupdate_nepali;
    private String visit_id;
    private String visit_lastdate_english;
    private String visit_lastdate_nepali;
    private Double visit_latitude;
    private Double visit_longitude;
    private Integer ward;
}
