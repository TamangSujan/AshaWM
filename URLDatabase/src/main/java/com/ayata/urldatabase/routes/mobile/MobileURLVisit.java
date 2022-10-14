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

    /**
        POST: Patient Visit Data
        1. Extract patient, check if patient exists or not
        2. If patient doesn't exist then create new patient else don't create
        3. Extract VisitList and create a new visit list
        4. Extract Visit, check if visit exists or not for the same user
        5. If visit doesn't exist then create new visit under the user else append modelPatient
        6. Respond 200 If all works
     */
    @PostMapping(value = "/addVisit")
    public ResponseEntity<?> addVisit(HttpServletRequest request) throws IOException {
        log.info("REQUEST: Add Visit");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AppUserList appUserList = objectMapper.readValue(request.getParameter("json"), AppUserList.class);
        if(appUserList!=null) {
            ModelPatientList modelPatientList = appUserList.getModelPatientList().get(0);
            //Patient Extracting from ModelPatientList
            Patients patient = patientRepository.getPatientById(modelPatientList.getPatientId());
            if (patient == null) {
                patient = new Patients();
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
                patientRepository.save(patient);
            }

            //Extracting and saving VisitList
            VisitLists visitList = new VisitLists();
            visitList.setVisit(modelPatientList.getModelVisitList());
            visitList.setPatientId(patient.getPatientId());
            visitList.setUser_id(patient.getUser());
            visitListsRepository.save(visitList);

            Visits visit = visitsRepository.getVisitByUserId(appUserList.getAppUserId());
            if (visit == null) {
                visit = new Visits();
                visit.setUser(appUserList.getAppUserId());
                ArrayList<AppUserList> appUserLists = new ArrayList<>();
                appUserLists.add(appUserList);
                visit.setAppUserList(appUserLists);
            }else{
                List<ModelVisitList> list = visit.getAppUserList().get(0).getModelPatientList().get(0).getModelVisitList();
                for (ModelVisitList modelVisit : modelPatientList.getModelVisitList()) {
                    if(modelVisit.getModelVisitChronic()!=null){
                        System.out.println(modelVisit.getModelVisitChronic());
                    }
                    list.add(modelVisit);
                }
            }
            visitsRepository.save(visit);
            log.info("SUCCESS: Adding Visit");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "Added"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "Null Data"));
        }
    }
}
