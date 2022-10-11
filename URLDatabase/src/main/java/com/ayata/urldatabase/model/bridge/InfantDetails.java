package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.database.InfantVisitLists;
import com.ayata.urldatabase.model.database.Infants;
import com.ayata.urldatabase.model.database.Patients;
import com.ayata.urldatabase.model.database.VisitLists;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfantDetails {
    private String infantId;
    private UsersShortDetail user;
    private String infantAddedDate;
    private String infantAge;
    private int infantAgeInDays;
    private int infantAgeInMonth;
    private int infantAgeInYear;
    private String infantDobEnglish;
    private String infantDobNepali;
    private String infantFirstName;
    private String infantFullName;
    private String infantLastName;
    private String infantGender;
    private String infantHouseno;
    private String infantPhone;
    private String infantVillagename;
    private String infantmotherfirstname;
    private String infantmotherlastname;
    private String infantwardno;
    private Double latitude;
    private Double longitude;

    private List<PastVisitDates> pastVisitDates;
    @JsonProperty("NextVisitDates")
    private List<NextVisitDates> nextVisitDates;
    private List<InfantVisitLists> infantVisitLists;

    public InfantDetails(Infants infant){
        this.infantAddedDate = infant.getInfantAddedDate();
        this.infantAge = infant.getInfantAge();
        this.infantFirstName = infant.getInfantFirstName();
        this.infantFullName = infant.getInfantFullName();
        this.infantGender = infant.getInfantGender();
        this.infantHouseno = infant.getInfantHouseno();
        this.infantId = infant.getInfantId();
        this.infantLastName = infant.getInfantLastName();
        this.infantPhone = infant.getInfantPhone();
        this.infantVillagename = infant.getInfantVillagename();
        this.infantmotherfirstname = infant.getInfantmotherfirstname();
        this.infantmotherlastname = infant.getInfantmotherlastname();
        this.infantDobEnglish = infant.getInfantDobEnglish();
        this.infantDobNepali = infant.getInfantDobNepali();
        this.infantAgeInDays = infant.getInfantAgeInDays();
        this.infantAgeInMonth = infant.getInfantAgeInMonth();
        this.infantAgeInYear = infant.getInfantAgeInYear();
    }
}
