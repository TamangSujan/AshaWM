package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.database.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CensusList {
    @Id
    private String _id;
    private String appuser_id;
    private EnteredDateTime entered_date_time;
    private boolean isSync;
    private Resident resident;
    private ResidentAddress residentAddress;
    private ResidentDetail residentDetail;
    private ResidentDetailFinal residentDetailFinal;
    private ResidentHead residentHead;
    private String resident_assign_id;
    private String resident_id;

    public Residents getNewResident(){
        Residents resident = new Residents();
        resident.setAppuser_id(this.getAppuser_id());
        resident.setSync(this.isSync());
        resident.setResidentId(this.getResident_id());
        resident.setResidentAssignId(this.getResident_assign_id());
        resident.setResident(this.getResident());
        resident.setResidentAddress(this.getResidentAddress());
        resident.setResidentDetail(this.getResidentDetail());
        resident.setResidentDetailFinal(this.getResidentDetailFinal());
        resident.setResidentHead(this.getResidentHead());
        resident.setEnteredDateTime(this.getEntered_date_time());
        return resident;
    }
}
