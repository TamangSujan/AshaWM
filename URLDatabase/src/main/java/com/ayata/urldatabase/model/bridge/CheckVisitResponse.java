package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.database.ModelPatientList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckVisitResponse {
    private String appUserId;
    private List<List<ModelPatientList>> modelPatientList;
}

