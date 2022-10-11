package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.database.Infants;
import com.ayata.urldatabase.model.database.Patients;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfantListResponse {
    private int resultPerPage;
    private int currentPage;
    private int total;
    private List<Infants> infantList;
}