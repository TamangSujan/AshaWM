package com.ayata.urldatabase.model.bridge;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CensusMap {
    @JsonProperty(value = "longitude")
    private Double longitude;
    @JsonProperty(value = "latitude")
    private Double latitude;
    @JsonProperty(value = "firstname")
    private String resident_first_name;
    @JsonProperty(value = "lastname")
    private String resident_last_name;
}
