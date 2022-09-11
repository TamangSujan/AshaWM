package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.database.ModelInfant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInfantResponse {
    private String appUserId;
    private List<List<ModelInfant>> modelInfantList;
}
