package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.database.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentOnly {
    private String residentId;
    private String residentAssignId;
    private Resident resident;
    private ResidentAddress residentAddress;
    private ResidentDetail residentDetail;
    private ResidentDetailFinal residentDetailFinal;
    private ResidentHead residentHead;
    private EnteredDateTime enteredDateTime;
}
