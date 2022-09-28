package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.bridge.CensusMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CensusMapResponse {
    private int total;
    private List<CensusMap> census;
}
