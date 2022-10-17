package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.Response.CheckInfantResponse;
import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.database.*;
import com.ayata.urldatabase.repository.InfantVisitListsRepository;
import com.ayata.urldatabase.repository.InfantVisitsRepository;
import com.ayata.urldatabase.repository.InfantsRepository;
import com.ayata.urldatabase.static_files.Library;
import com.ayata.urldatabase.static_files.StatusCode;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class MobileURLInfant {
    private InfantVisitListsRepository infantVisitListsRepository;
    private InfantVisitsRepository infantVisitsRepository;
    private InfantsRepository infantsRepository;
    private static final Logger log = LogManager.getLogger(MobileURLInfant.class);

    @PostMapping("/addInfantVisit")
    public ResponseEntity<?> addVisit(@RequestBody InfantAppUserList appUserList) {
        log.info("REQUEST: Add Infant Visit");
        if (appUserList != null) {
            ModelInfant modelInfant = appUserList.getModelInfants().get(0);
            //Extracting infant from modelInfant
            Infants infant = infantsRepository.findInfantById(modelInfant.getInfantId());
            if (infant == null) {
                infant = new Infants();
                infant.setUser(appUserList.getAppUserId());
                infant.setInfantAddedDate(modelInfant.getInfantAddedDate());
                infant.setInfantAge(modelInfant.getInfantAge());
                infant.setInfantAgeInDays(modelInfant.getInfantAgeInDays());
                infant.setInfantAgeInMonth(modelInfant.getInfantAgeInMonth());
                infant.setInfantDobEnglish(modelInfant.getInfantDobEnglish());
                infant.setInfantDobNepali(modelInfant.getInfantDobNepali());
                infant.setInfantFirstName(modelInfant.getInfantFirstName());
                infant.setInfantFullName(modelInfant.getInfantFullName());
                infant.setInfantGender(modelInfant.getInfantGender());
                infant.setInfantHouseno(modelInfant.getInfantHouseno());
                infant.setInfantId(modelInfant.getInfantId());
                infant.setInfantLastName(modelInfant.getInfantLastName());
                infant.setInfantPhone(modelInfant.getInfantPhone());
                infant.setInfantVillagename(modelInfant.getInfantVillagename());
                infant.setInfantmotherfirstname(modelInfant.getInfantmotherfirstname());
                infant.setInfantmotherlastname(modelInfant.getInfantmotherlastname());
                infant.setInfantwardno(modelInfant.getInfantwardno());
                infantsRepository.save(infant);
            }
            InfantVisitLists visitList = new InfantVisitLists();
            visitList.setInfantVisit((ArrayList<InfantVisit>) modelInfant.getModelVisitList());
            visitList.setInfantId(infant.getInfantId());
            visitList.setUser_id(infant.getUser());
            infantVisitListsRepository.save(visitList);

            InfantVisits visit = infantVisitsRepository.getVisitByUserId(appUserList.getAppUserId());
            if (visit == null) {
                visit = new InfantVisits();
                visit.setUser(appUserList.getAppUserId());
                ArrayList<InfantAppUserList> appUserLists = new ArrayList<>();
                appUserLists.add(appUserList);
                visit.setAppUserList(appUserLists);
            } else {
                List<InfantVisit> list = visit.getAppUserList().get(0).getModelInfants().get(0).getModelVisitList();
                list.addAll(modelInfant.getModelVisitList());
            }
            infantVisitsRepository.save(visit);
            log.info("SUCCESS: Adding Infant Visit");
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Added"));
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "Infant Data not found on database!"));
        }
    }

    @PostMapping("/checkInfantVisit")
    public ResponseEntity<?> checkInfantVisit(@RequestBody List<String> list){
        log.info("REQUEST: Check Infant Visit");
        String user = Library.splitAndGetFirst(list.get(0), "_");
        CheckInfantResponse response = new CheckInfantResponse(user, new ArrayList<>());
        List<InfantVisits> visits = infantVisitsRepository.getInfantVisitExceptGivenList(user, list);
        for(InfantVisits infantVisit: visits){
            for(InfantAppUserList appUserList: infantVisit.getAppUserList()){
                response.getModelInfantList().add(appUserList.getModelInfants().get(0));
            }
        }
        log.info("SUCCESS: Sending Infant Visit");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
