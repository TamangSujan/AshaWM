package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.database.VisitLists;
import com.ayata.urldatabase.repository.VisitListsRepository;
import com.ayata.urldatabase.static_files.StatusCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
        if(list!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, null, null, list));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "List not found!"));
    }

    @GetMapping("/chronicdisease")
    public ResponseEntity<?> getChronicPatient(){
        Object list = visitListsRepository.getChronicPatients();
        if(list!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, null, null, list));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "List not found!"));
    }

    @GetMapping("/riskncd")
    public ResponseEntity<?> getRiskPatient(){
        Object list = visitListsRepository.getRiskPatients();
        if(list!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, null, null, list));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "List not found!"));
    }
}
