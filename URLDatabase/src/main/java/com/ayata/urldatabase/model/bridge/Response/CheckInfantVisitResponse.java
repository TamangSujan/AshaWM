package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.database.InfantVisits;
import com.ayata.urldatabase.model.database.ModelInfant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInfantVisitResponse {
    private String appUserId;
    private List<InfantVisits> modelInfantList;
}
