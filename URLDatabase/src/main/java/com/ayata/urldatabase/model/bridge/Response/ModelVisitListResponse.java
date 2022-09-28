package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.database.ModelVisitList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelVisitListResponse {
    private String appUserId;
    private ModelVisitList modelVisitList;
}
