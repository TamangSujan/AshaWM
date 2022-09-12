package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.CheckInfantResponse;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.database.*;
import com.ayata.urldatabase.repository.InfantVisitListsRepository;
import com.ayata.urldatabase.repository.InfantVisitsRepository;
import com.ayata.urldatabase.repository.InfantsRepository;
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
public class MobileURLInfant {
    private InfantVisitListsRepository infantVisitListsRepository;
    private InfantVisitsRepository infantVisitsRepository;
    private InfantsRepository infantsRepository;
    @PostMapping("/addInfantVisit")
    public ResponseEntity<?> addVisit(@RequestBody InfantAppUserList appUserList){
        List<Infants> infants = new ArrayList<>();
        List<InfantVisitLists> visitLists = new ArrayList<>();
        for(ModelInfant modelInfant: appUserList.getModelInfants()){
            Infants infant = new Infants();
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
            infants.add(infant);

            InfantVisitLists visitList = new InfantVisitLists();
            visitList.setInfantVisit((ArrayList<InfantVisit>) modelInfant.getModelVisitList());
            visitList.setInfantId(infant.getInfantId());
            visitList.setUser_id(infant.getUser());
            visitLists.add(visitList);
        }
        List<String> infantPhones = new ArrayList<>();
        for(int i=0; i<infants.size(); i++){
            infantPhones.add(infants.get(i).getInfantPhone());
        }
        List<Infants> matchedInfant = infantsRepository.matchedPhoneList(infantPhones);
        for(Infants mpatient: matchedInfant){
            for(Infants patient: infants){
                if(mpatient.getInfantPhone().equals(patient.getInfantPhone())){
                    patient.set_id(mpatient.get_id());
                    break;
                }
            }
        }
        /*
        List<Infants> unmatchedInfant = infants.stream().
                filter(infant->{
                    for(Infants i: matchedInfant){
                        if(infant.getInfantPhone().equals(i.getInfantPhone())){
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());*/
        infantsRepository.saveAll(infants);
        infantVisitListsRepository.saveAll(visitLists);
        InfantVisits visit = new InfantVisits();
        visit.setUser(appUserList.getAppUserId());
        ArrayList<InfantAppUserList> appList = new ArrayList<>();
        appList.add(appUserList);
        visit.setAppUserList(appList);
        infantVisitsRepository.save(visit);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "success", "Added"));
    }

    @PostMapping("/checkInfantVisit")
    public ResponseEntity<?> checkInfantVisit(@RequestBody List<String> list){
        String user = Library.splitAndGetFirst(list.get(0), "_");
        CheckInfantResponse response = new CheckInfantResponse(user, new ArrayList<>());
        List<InfantVisits> visits = infantVisitsRepository.getInfantVisitExceptGivenList(user, list);
        for(InfantVisits infantVisit: visits){
            for(InfantAppUserList appUserList: infantVisit.getAppUserList()){
                response.getModelInfantList().add(appUserList.getModelInfants().get(0));
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
