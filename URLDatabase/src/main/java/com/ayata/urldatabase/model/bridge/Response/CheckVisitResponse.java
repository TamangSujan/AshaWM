package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.database.ModelPatientList;
import com.ayata.urldatabase.model.database.ModelVisitList;
import com.ayata.urldatabase.model.database.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckVisitResponse {
    private String appUserId;
    private Object modelPatientList;
}

