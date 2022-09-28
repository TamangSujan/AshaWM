package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.database.Residents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CensusWebResponse {
    private int perPage;
    private int currentPage;
    private int total;
    private List<Residents> censusList;
}
