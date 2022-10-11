package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.*;
import com.ayata.urldatabase.model.bridge.Response.InfantListResponse;
import com.ayata.urldatabase.model.bridge.Response.PatientListResponse;
import com.ayata.urldatabase.model.database.*;
import com.ayata.urldatabase.model.token.Message;
import com.ayata.urldatabase.repository.InfantVisitListsRepository;
import com.ayata.urldatabase.repository.InfantsRepository;
import com.ayata.urldatabase.repository.UserRepository;
import com.ayata.urldatabase.static_methods.Library;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/web/Infant")
public class URLInfant {

    private InfantsRepository infantsRepository;
    private InfantVisitListsRepository infantVisitListsRepository;
    private UserRepository userRepository;

    @GetMapping("/get")
    public ResponseEntity<?> getInfantList(@RequestParam int perPage, @RequestParam int currentPage){
        if(currentPage<=0){
            currentPage = 1;
        }
        List<Infants> list = infantsRepository.getLimitInfant(perPage, (currentPage - 1) * perPage);
        if( list.size()>0) {
            return ResponseEntity.status(HttpStatus.OK).body(new InfantListResponse(perPage, currentPage, infantsRepository.getTotalInfant(), list));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message("Patient List not found in database."));
    }

    @GetMapping("/total")
    public ResponseEntity<?> totalInfant(){
        return ResponseEntity.status(200).body(new ResponseDetails(200, "Success", "", infantsRepository.getTotalInfant()));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getInfant(@PathVariable(value = "id")String id){
        String chwId = Library.splitAndGetFirst(id, "_");
        Infants infant =  infantsRepository.findInfantById(id);
        if(infant!=null){
            List<InfantVisitLists> visitLists = infantVisitListsRepository.getInfantVisitsList(chwId, id);
            InfantDetails infantDetails = new InfantDetails(infant);

            //For Null Safety
            infantDetails.setLatitude(0.00);
            infantDetails.setLongitude(0.00);
            List<PastVisitDates> pastVisitDatesList = new ArrayList<>();
            List<NextVisitDates> nextVisitDatesList = new ArrayList<>();
            infantDetails.setPastVisitDates(pastVisitDatesList);
            infantDetails.setNextVisitDates(nextVisitDatesList);
            infantDetails.setInfantVisitLists(new ArrayList<>());

            Users user = userRepository.findByChwId(Integer.parseInt(chwId));
            if(user!=null) {
                UsersShortDetail usersShortDetail = new UsersShortDetail();
                usersShortDetail.set_id(user.get_id());
                usersShortDetail.setChw_name(user.getChw_name());
                infantDetails.setUser(usersShortDetail);
            }
            if(visitLists!=null && visitLists.size()>0){
                infantDetails.setLatitude(visitLists.get(0).getInfantVisit().get(0).getVisit_latitude());
                infantDetails.setLongitude(visitLists.get(0).getInfantVisit().get(0).getVisit_longitude());
                for(InfantVisitLists visitList: visitLists){
                    for(InfantVisit modelVisitList: visitList.getInfantVisit()){
                        PastVisitDates pastVisitDates = new PastVisitDates();
                        NextVisitDates nextVisitDates = new NextVisitDates();
                        if(modelVisitList!=null){
                            pastVisitDates.setVisit_lastdate_english(modelVisitList.getVisit_lastdate_english());
                            pastVisitDates.setVisit_lastdate_nepali(modelVisitList.getVisit_lastdate_nepali());
                            nextVisitDates.setVisit_followupdate_english(modelVisitList.getVisit_followupdate_english());
                            nextVisitDates.setVisit_followupdate_nepali(modelVisitList.getVisit_followupdate_nepali());
                            pastVisitDatesList.add(pastVisitDates);
                            nextVisitDatesList.add(nextVisitDates);
                        }
                    }
                }
                infantDetails.setPastVisitDates(pastVisitDatesList);
                infantDetails.setNextVisitDates(nextVisitDatesList);
                infantDetails.setInfantVisitLists(visitLists);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData("200", "Success", infantDetails));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "No patient found!"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInfant(@PathVariable(value = "id")String id, @RequestBody Infants infant){
        Infants inf = infantsRepository.findInfantById(id);
        if(inf!=null) {
            infant.set_id(inf.get_id());
            infantsRepository.save(infant);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData("200", "Success", infant));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message("Infant not found in database."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> updateInfant(@PathVariable(value = "id")String id){
        Infants infant = infantsRepository.findInfantById(id);
        if(infant!=null) {
            infantsRepository.delete(infant);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "Infant deleted successfully!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message("Infant not found in database."));
    }
}
