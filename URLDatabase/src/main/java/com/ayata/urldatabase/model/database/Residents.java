package com.ayata.urldatabase.model.database;

import com.ayata.urldatabase.model.bridge.ResidentOnly;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Residents{
    @Id
    private String _id;
    private String appuser_id;
    private boolean isSync;
    private String resident_id;
    private String resident_assign_id;
    private Resident resident;
    private ResidentAddress residentAddress;
    private ResidentDetail residentDetail;
    private ResidentDetailFinal residentDetailFinal;
    private ResidentHead residentHead;
    private EnteredDateTime enteredDateTime;
    public ResidentOnly getResidentOnly(){
        ResidentOnly residentOnly = new ResidentOnly();
        residentOnly.setSync(isSync);
        residentOnly.setAppuser_id(appuser_id);
        residentOnly.setResident_id(resident_id);
        residentOnly.setResident_assign_id(resident_assign_id);
        residentOnly.setResident(resident);
        residentOnly.setResidentAddress(residentAddress);
        residentOnly.setResidentDetail(residentDetail);
        residentOnly.setResidentDetailFinal(residentDetailFinal);
        residentOnly.setResidentHead(residentHead);
        residentOnly.setEnteredDateTime(enteredDateTime);
        return residentOnly;
    }
}
