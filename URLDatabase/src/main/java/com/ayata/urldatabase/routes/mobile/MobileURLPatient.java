package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.CheckInfantResponse;
import com.ayata.urldatabase.model.bridge.CheckVisitResponse;
import com.ayata.urldatabase.model.database.AppUserList;
import com.ayata.urldatabase.model.database.InfantAppUserList;
import com.ayata.urldatabase.model.database.InfantVisits;
import com.ayata.urldatabase.model.database.Visits;
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
        String user = Library.splitAndGetFirst(list.get(0), "_");
        List<Visits> visits = visitsRepository.getVisitsExceptGivenList(user, list);
        CheckVisitResponse response = new CheckVisitResponse(user, new ArrayList<>());
        for(Visits visit: visits){
            for(AppUserList appUserList: visit.getAppUserList()){
                response.getModelPatientList().add(appUserList.getModelPatientList());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
