package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.database.Visit;
import com.ayata.urldatabase.model.database.VisitLists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PastVisitDates {
    private String visit_lastdate_english;
    private String visit_lastdate_nepali;
    private String health_facility_name;
    private String visit_category;
}
