package com.ayata.urldatabase.model.bridge;

import com.ayata.urldatabase.model.Patients;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientListResponse {
    private int resultPerPage;
    private int currentPage;
    private int total;
    private List<Patients> patientsList;
}