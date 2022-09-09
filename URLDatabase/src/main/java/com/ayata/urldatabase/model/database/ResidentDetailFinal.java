package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDetailFinal{
    private String resident_ethnicity;
    private Integer resident_no_of_people;
    private String resident_respondent_aggreement;
    private String resident_taken_interview_previously;
}
