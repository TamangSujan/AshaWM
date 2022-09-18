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
    public EnteredDateTime entered_date_time;
    public boolean isSync;
    public ModelVisitChronic modelVisitChronic;
    public ModelVisitSafe modelVisitSafe;
    public String patient_id;
    public String visit_category;
    public String visit_followupdate_english;
    public String visit_followupdate_nepali;
    public String visit_id;
    public String visit_lastdate_english;
    public String visit_lastdate_nepali;
    public Double visit_latitude;
    public Double visit_longitude;
    public Integer ward;
}
