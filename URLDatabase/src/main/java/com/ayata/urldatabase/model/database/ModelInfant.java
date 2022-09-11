package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelInfant{
    private String infantAddedDate;
    private String infantAge;
    private int infantAgeInDays;
    private int infantAgeInMonth;
    private int infantAgeInYear;
    private String infantDobEnglish;
    private String infantDobNepali;
    private String infantFirstName;
    private String infantFullName;
    private String infantGender;
    private String infantHouseno;
    private String infantId;
    private String infantLastName;
    private String infantPhone;
    private String infantVillagename;
    private String infantmotherfirstname;
    private String infantmotherlastname;
    private int infantwardno;
    private double latitude;
    private double longitude;
    private Object modelVisitList;
}
