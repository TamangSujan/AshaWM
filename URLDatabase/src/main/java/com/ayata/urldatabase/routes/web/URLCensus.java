package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.database.Residents;
import com.ayata.urldatabase.model.bridge.CheckCensusResponse;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.token.Message;
import com.ayata.urldatabase.repository.ResidentsRepository;
import com.ayata.urldatabase.static_methods.Library;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class URLCensus {
    private ResidentsRepository residentsRepository;
    @PostMapping("/addCensus")
    public ResponseEntity<?> addCensus(@RequestBody Residents residents){
        Residents checkResident = residentsRepository.findByResidentId(residents.getResident_id());
        if(checkResident!=null){
            return new ResponseEntity(new ResponseMessage("403", "Failure", "Resident Exists"), HttpStatus.OK);
        }else{
            residentsRepository.save(residents);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "Resident Added"));
        }
    }

    @PostMapping("/checkCensus")
    public ResponseEntity<?> checkCensus(@RequestBody List<String> residents){
        if(residents.isEmpty()) {
            return new ResponseEntity(new ResponseMessage("403", "Failure", "Empty Resident's List "), HttpStatus.OK);
        }
        String appUserId = Library.splitAndGetFirst(residents.get(0), "_");
        List<Residents> checkResident = residentsRepository.findAllByUserIdExceptGivenList(appUserId, residents);
        CheckCensusResponse response = new CheckCensusResponse(appUserId, new ArrayList<>());
        for(Residents resident: checkResident){
            response.getCensusList().add(resident.getResidentOnly());
        }
        if(checkResident.size()>0){
            return new ResponseEntity(response, HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Message("No New Data"));
        }
    }
}
