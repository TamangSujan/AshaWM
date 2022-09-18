package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.database.*;
import com.ayata.urldatabase.repository.PatientRepository;
import com.ayata.urldatabase.repository.VisitListsRepository;
import com.ayata.urldatabase.repository.VisitsRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/mobile")
public class MobileURLVisit {
    private PatientRepository patientRepository;
    private VisitsRepository visitsRepository;
    private VisitListsRepository visitListsRepository;
    private static Logger log = LogManager.getLogger(MobileURLVisit.class);
    @PostMapping(value = "/addVisit")
    public ResponseEntity<?> addVisit(HttpServletRequest request) throws ServletException, IOException {
        log.info("REQUEST: Add Visit");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AppUserList appUserList = objectMapper.readValue(request.getParameter("json"), AppUserList.class);
        System.out.println(appUserList.toString());
        List<Patients> patients = new ArrayList<>();
        List<VisitLists> visitLists = new ArrayList<>();
        for(ModelPatientList modelPatientList: appUserList.getModelPatientList()){
            Patients patient = new Patients();
            patient.setUser(appUserList.getAppUserId());
            patient.setPatientAddedDate(modelPatientList.getPatientAddedDate());
            patient.setPatientAge(modelPatientList.getPatientAge());
            patient.setPatientDob(modelPatientList.getPatientDob());
            patient.setPatientFirstName(modelPatientList.getPatientFirstName());
            patient.setPatientFullName(modelPatientList.getPatientFullName());
            patient.setPatientGender(modelPatientList.getPatientGender());
            patient.setPatientHouseno(modelPatientList.getPatientHouseno());
            patient.setPatientId(modelPatientList.getPatientId());
            patient.setPatientLastName(modelPatientList.getPatientLastName());
            patient.setPatientMunicipality(modelPatientList.getPatientMunicipality());
            patient.setPatientPhone(modelPatientList.getPatientPhone());
            patient.setPatientSpouseFullName(modelPatientList.getPatientSpouseFullName());
            patient.setPatientVillagename(modelPatientList.getPatientVillagename());
            patient.setPatientspousefirstname(modelPatientList.getPatientspousefirstname());
            patient.setPatientspouselastname(modelPatientList.getPatientspouselastname());
            patient.setPatientwardno(modelPatientList.getPatientwardno());
            patient.setImage("");
            patients.add(patient);

            VisitLists visitList = new VisitLists();
            visitList.setVisit(modelPatientList.getModelVisitList());
            visitList.setPatientId(patient.getPatientId());
            visitList.setUser_id(patient.getUser());
            visitLists.add(visitList);
        }
        List<String> patientPhones = new ArrayList<>();
        for(int i=0; i<patients.size(); i++){
            patientPhones.add(patients.get(i).getPatientPhone());
        }
        List<Patients> matchedPatient = patientRepository.matchedPhoneList(patientPhones);
        for(Patients mpatient: matchedPatient){
            for(Patients patient: patients){
                if(mpatient.getPatientPhone().equals(patient.getPatientPhone())){
                    patient.set_id(mpatient.get_id());
                    break;
                }
            }
        }
        /*
        List<String> visitPatientId = new ArrayList<>();
        for(int i=0; i<visitLists.size(); i++){
            visitPatientId.add(visitLists.get(i).getUser_id());
        }
        List<VisitLists> matchVisitList = visitListsRepository.getVisitsOfGivenList(visitUserId);
        */
        patientRepository.saveAll(patients);
        visitListsRepository.saveAll(visitLists);
        Visits visit = new Visits();
        visit.setUser(appUserList.getAppUserId());
        ArrayList<AppUserList> appList = new ArrayList<>();
        appList.add(appUserList);
        visit.setAppUserList(appList);
        visitsRepository.save(visit);
        log.info("SUCCESS: Adding Visit");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "success", "Added"));
    }
}
