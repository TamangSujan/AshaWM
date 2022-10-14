package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.Response.CheckInfantResponse;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.database.*;
import com.ayata.urldatabase.repository.InfantVisitListsRepository;
import com.ayata.urldatabase.repository.InfantVisitsRepository;
import com.ayata.urldatabase.repository.InfantsRepository;
import com.ayata.urldatabase.static_methods.Library;
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
    private static Logger log = LogManager.getLogger(MobileURLInfant.class);

    /**
     POST: Infant Visit Data
     1. Extract infant, check if infant exists or not
     2. If infant doesn't exist then create new infant else don't create
     3. Extract InfantList and create a new visit list
     4. Extract InfantVisit, check if visit exists or not for the same user
     5. If InfantVisit doesn't exist then create new visit under the user else append modelVisit
     6. Respond 200 If all works
     */
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
                for (InfantVisit modelVisit : modelInfant.getModelVisitList()) {
                    list.add(modelVisit);
                }
            }
            infantVisitsRepository.save(visit);
            log.info("SUCCESS: Adding Infant Visit");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "Added"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "Null Data"));
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
