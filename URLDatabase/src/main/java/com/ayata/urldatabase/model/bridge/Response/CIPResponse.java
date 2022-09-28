package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.database.Infants;
import com.ayata.urldatabase.model.database.Patients;
import com.ayata.urldatabase.model.database.Residents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CIPResponse {
    private int perPage;
    private int currentPage;
    private int total;
    private List<Patients> patients;
    private List<Infants> infants;
    private List<Residents> census;

    public CIPResponse(int perPage, int currentPage, int total){
        this.perPage = perPage;
        this.currentPage = currentPage;
        this.total = total;
    }
}