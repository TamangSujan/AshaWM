package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.CheckVisitResponse;
import com.ayata.urldatabase.model.database.*;
import com.ayata.urldatabase.repository.VisitListsRepository;
import com.ayata.urldatabase.repository.VisitsRepository;
import com.ayata.urldatabase.static_methods.Library;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/mobile")
public class MobileURLPatient {
    private VisitsRepository visitsRepository;
    @PostMapping("/checkVisit")
    public ResponseEntity<?> checkVisits(@RequestBody List<String> list){
        //logger.info("Inside Check Visit");
        String user = Library.splitAndGetFirst(list.get(0), "_");
        List<Visits> visits = visitsRepository.getVisitsExceptGivenList(user, list);
        //List<VisitLists> visitLists = visitListsRepository.getVisitsExceptGivenList(user, list);/*
        List<Object> patientLists = new ArrayList<>();
        for(Visits visit: visits){
            for(AppUserList appUserList: visit.getAppUserList()) {
                for(ModelPatientList modelPatientList: appUserList.getModelPatientList()){
                    patientLists.add(modelPatientList);
                }
            }
        }
        CheckVisitResponse response = new CheckVisitResponse(user, patientLists);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
