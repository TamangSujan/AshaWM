package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.bridge.ResidentOnly;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckCensusResponse {
    private String appUserId;
    private List<ResidentOnly> censusList;
}
