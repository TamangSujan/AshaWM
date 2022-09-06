package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.Residents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckCensusResponse {
    private String appUserId;
    private List<Residents> censusList;
}
