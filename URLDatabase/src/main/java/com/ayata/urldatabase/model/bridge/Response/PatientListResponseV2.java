package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.bridge.PatientShortDetails;
import com.ayata.urldatabase.model.database.Patients;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientListResponseV2 {
    private int resultPerPage;
    private int currentPage;
    private int total;
    private List<PatientShortDetails> patients;
}