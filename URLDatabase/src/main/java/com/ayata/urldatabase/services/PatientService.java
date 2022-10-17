package com.ayata.urldatabase.services;

import com.ayata.urldatabase.model.bridge.*;
import com.ayata.urldatabase.model.bridge.Response.CIPResponse;
import com.ayata.urldatabase.model.bridge.Response.PatientListResponseV2;
import com.ayata.urldatabase.model.database.Patients;
import com.ayata.urldatabase.repository.PatientRepository;
import com.ayata.urldatabase.repository.VisitListsRepository;
import com.ayata.urldatabase.routes.web.misc.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {
    private PatientRepository patientRepository;
    private VisitListsRepository visitListsRepository;
    public CIPResponse getPatients(int perPage, int currentPage, String id){
        List<Patients> patient = patientRepository.getLimitPatientByUser(perPage, (currentPage - 1) * perPage, id);
        CIPResponse response = new CIPResponse(perPage, currentPage, patientRepository.getTotalPatient());
        response.setPatients(patient);
        return response;
    }

    public PatientListResponseV2 getPatientShortDetails(int perPage, int currentPage){
        List<PatientShortDetails> patientShortDetails = patientRepository.patientShortDetails(perPage, currentPage);
        Integer total = patientRepository.getTotalPatient();
        if(total==null){
            total = 0;
        }
        return new PatientListResponseV2(perPage, currentPage, total, patientShortDetails);
    }
}
