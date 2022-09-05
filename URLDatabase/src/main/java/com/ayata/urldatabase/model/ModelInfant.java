package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelInfant{
    private String infantAddedDate;
    private Integer infantAge;
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
    private String infantwardno;
    private ArrayList<ModelVisitList> modelVisitList;
}
