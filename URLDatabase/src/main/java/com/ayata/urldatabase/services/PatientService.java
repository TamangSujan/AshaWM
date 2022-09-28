package com.ayata.urldatabase.services;

import com.ayata.urldatabase.model.bridge.*;
import com.ayata.urldatabase.model.bridge.Response.CIPResponse;
import com.ayata.urldatabase.model.bridge.Response.PatientListResponseV2;
import com.ayata.urldatabase.model.database.Patients;
import com.ayata.urldatabase.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {
    private PatientRepository patientRepository;
    public CIPResponse getPatients(int perPage, int currentPage, String id){
        List<Patients> patient = patientRepository.getLimitPatientByUser(perPage, (currentPage - 1) * perPage, id);
        CIPResponse response = new CIPResponse(perPage, currentPage, patientRepository.getTotalPatient());
        response.setPatients(patient);
        return response;
    }

    public PatientListResponseV2 getPatientShortDetails(int perPage, int currentPage){
        List<PatientShortDetails> patientShortDetails = patientRepository.patientShortDetails(perPage, currentPage);
        PatientListResponseV2 response = new PatientListResponseV2(perPage, currentPage, patientRepository.getTotalPatient(), patientShortDetails);
        return response;
    }

//    public List<List<?>> getPatientsChart(String chw_id){
//        List<PatientChartList> list = patientRepository.getChart(chw_id);
//        List<String> x = new ArrayList<>();
//        List<Integer> y = new ArrayList<>();
//        for (PatientChartList patientChart : list) {
//            x.add(patientChart.get_id());
//            y.add(patientChart.getCount());
//        }
//        List<List<?>> listOfList = new ArrayList<>();
//        listOfList.add(list);
//        listOfList.add(x);
//        listOfList.add(y);
//        return listOfList;
//    }
}
