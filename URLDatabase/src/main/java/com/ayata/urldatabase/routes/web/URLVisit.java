package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.database.VisitLists;
import com.ayata.urldatabase.repository.VisitListsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/web/visit")
public class URLVisit {
    private VisitListsRepository visitListsRepository;

    @GetMapping("/followupdate")
    public ResponseEntity<?> getFollowUpdate(){
        Object list = visitListsRepository.getFollowUpdate();
        FinalResponse response = new FinalResponse("400", "Failure");
        if(list!=null){
            response.setStatusCode("200", "Success");
            response.setDetails(list);
            return ResponseEntity.status(200).body(response);
        }
        response.setMessage("List not found!");
        return ResponseEntity.status(400).body(response);
    }

    @GetMapping("/chronicdisease")
    public ResponseEntity<?> getChronicPatient(){
        Object list = visitListsRepository.getChronicPatients();
        FinalResponse response = new FinalResponse("400", "Failure");
        if(list!=null){
            response.setStatusCode("200", "Success");
            response.setDetails(list);
            return ResponseEntity.status(200).body(response);
        }
        response.setMessage("List not found!");
        return ResponseEntity.status(400).body(response);
    }

    @GetMapping("/riskncd")
    public ResponseEntity<?> getRiskPatient(){
        Object list = visitListsRepository.getRiskPatients();
        FinalResponse response = new FinalResponse("400", "Failure");
        if(list!=null){
            response.setStatusCode("200", "Success");
            response.setDetails(list);
            return ResponseEntity.status(200).body(response);
        }
        response.setMessage("List not found!");
        return ResponseEntity.status(400).body(response);
    }
}
