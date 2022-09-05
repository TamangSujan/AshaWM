package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfantVisit{
    private ICheckup iCheckup;
    private ICheckupMore iCheckupMore;
    private ArrayList<ICondition> iCondition;
    private IDiarrhoea iDiarrhoea;
    private IHeartRate iHeartRate;
    private IJaundice iJaundice;
    private IMother iMother;
    private IOralUlcer iOralUlcer;
    private IRisk iRisk;
    private InfantTraining infantTraining;
    private String infant_id;
    private String visit_followupdate_english;
    private String visit_followupdate_nepali;
    private String visit_id;
    private String visit_lastdate_english;
    private String visit_lastdate_nepali;
    private String ward;
}
