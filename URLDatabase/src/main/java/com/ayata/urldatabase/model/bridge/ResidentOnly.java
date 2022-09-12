package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.database.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentOnly {
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
}
