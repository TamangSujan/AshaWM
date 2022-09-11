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
    private String user;
    private String residentId;
    private String residentAssignId;
    private Resident resident;
    private ResidentAddress residentAddress;
    private ResidentDetail residentDetail;
    private ResidentDetailFinal residentDetailFinal;
    private ResidentHead residentHead;
    private EnteredDateTime enteredDateTime;
    public ResidentOnly getResidentOnly(){
        ResidentOnly residentOnly = new ResidentOnly();
        residentOnly.setResidentId(residentId);
        residentOnly.setResidentAssignId(residentAssignId);
        residentOnly.setResident(resident);
        residentOnly.setResidentAddress(residentAddress);
        residentOnly.setResidentDetail(residentDetail);
        residentOnly.setResidentDetailFinal(residentDetailFinal);
        residentOnly.setResidentHead(residentHead);
        residentOnly.setEnteredDateTime(enteredDateTime);
        return residentOnly;
    }
}
