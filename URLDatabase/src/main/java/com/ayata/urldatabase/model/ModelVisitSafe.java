package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelVisitSafe{
    private ArrayList<Object> babyDetailList;
    private Checkup checkup;
    private ConceptionRecord conceptionRecord;
    private DeliveryDetail deliveryDetail;
    private HealthDetail healthDetail;
    private Lab lab;
    private MenstrualDate menstrualDate;
    private PregnancyHistory pregnancyHistory;
    private String pregnancyTermination;
    private PreventiveCare preventiveCare;
    private Termination termination;
    private Usg usg;
    private String visitTime;
    private String visitType;
}
