package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.CensusRoot;
import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.database.Residents;
import com.ayata.urldatabase.model.bridge.Response.CheckCensusResponse;
import com.ayata.urldatabase.repository.ResidentsRepository;
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
public class MobileURLCensus {
    private ResidentsRepository residentsRepository;
    private static final Logger log = LogManager.getLogger(MobileURLCensus.class);

    @PostMapping("/checkCensus")
    public ResponseEntity<?> checkMobileCensus(@RequestBody List<String> residents){
        log.info("REQUEST: Check Census");
        try {
            String appUserId = Library.splitAndGetFirst(residents.get(0), "_");
            List<Residents> checkResident = residentsRepository.findAllByUserIdExceptGivenList(appUserId, residents);
            CheckCensusResponse response = new CheckCensusResponse(appUserId, new ArrayList<>());
            for (Residents resident : checkResident) {
                response.getCensusList().add(resident.getResidentOnly());
            }
            log.info("SUCCESS: Sending Census");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (RuntimeException ex){
            log.error("ERROR: Check census!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FinalResponse(StatusCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
        }
    }

    @PostMapping("/addCensus")
    public ResponseEntity<?> addCensus(@RequestBody CensusRoot censusRoot){
        log.info("REQUEST: Add Census");
        Residents checkResident = residentsRepository.findByResidentId(censusRoot.censusList.get(0).getResident_id());
        if(checkResident!=null){
            log.error("ERROR: Add Census - Resident Exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FinalResponse(StatusCode.BAD_REQUEST, "Resident Exists"));
        }else{
            log.info("SUCCESS: Adding Census");
            residentsRepository.save(censusRoot.censusList.get(0).getNewResident());
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Resident Added"));
        }
    }
}
