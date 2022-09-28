package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.bridge.LocationPatient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCensusResponse {
    private Integer total;
    private List<LocationPatient> census;
}
