package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value="visitsVisitSafe")
public class Visit {
    private ModelVisitChronic modelVisitChronic;
    private String patient_id;
    private String visit_category;
    private Date visit_followupdate_english;
    private String visit_followupdate_nepali;
    private String visit_id;
    private Date visit_lastdate_english;
    private String visit_lastdate_nepali;
    private String ward;
}
