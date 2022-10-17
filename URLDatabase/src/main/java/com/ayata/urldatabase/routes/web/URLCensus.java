package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.*;
import com.ayata.urldatabase.model.bridge.Response.CensusMapResponse;
import com.ayata.urldatabase.model.bridge.Response.CensusWebResponse;
import com.ayata.urldatabase.model.bridge.Response.CheckCensusResponse;
import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.database.Residents;
import com.ayata.urldatabase.model.token.Message;
import com.ayata.urldatabase.repository.PatientRepository;
import com.ayata.urldatabase.repository.ResidentsRepository;
import com.ayata.urldatabase.static_files.Library;
import com.ayata.urldatabase.static_files.StatusCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/web/Census")
public class URLCensus {
    private ResidentsRepository residentsRepository;
    private PatientRepository patientRepository;
    @PostMapping("/addCensus")
    public ResponseEntity<?> addCensus(@RequestBody Residents residents){
        Residents checkResident = residentsRepository.findByResidentId(residents.getResidentId());
        if(checkResident!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FinalResponse(StatusCode.BAD_REQUEST, "Resident Exists"));
        }else{
            residentsRepository.save(residents);
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Resident Added"));
        }
    }

    @PostMapping("/checkCensus")
    public ResponseEntity<?> checkCensus(@RequestBody List<String> residents) {
        if (residents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FinalResponse(StatusCode.BAD_REQUEST, "Empty Resident's List "));
        }
        String appUserId = Library.splitAndGetFirst(residents.get(0), "_");
        List<Residents> checkResident = residentsRepository.findAllByUserIdExceptGivenList(appUserId, residents);
        CheckCensusResponse response = new CheckCensusResponse(appUserId, new ArrayList<>());
        for (Residents resident : checkResident) {
            response.getCensusList().add(resident.getResidentOnly());
        }
        if (checkResident.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "No data found in database!"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllCensusParam(HttpServletRequest request){
        int perPage = 10;
        int currentPage = 1;
        String perPageString = request.getParameter("perPage");
        String currentPageString = request.getParameter("currentPage");
        if(perPageString!=null)
            perPage = Integer.parseInt(perPageString);
        if(currentPageString!=null)
            currentPage = Integer.parseInt(currentPageString);
        List<Residents> residentsList = residentsRepository.getLimitResident(perPage, (currentPage-1)*perPage);
        if(residentsList!=null && residentsList.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new CensusWebResponse(perPage, currentPage, residentsRepository.getTotalResident(), residentsList));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FinalResponse(StatusCode.BAD_REQUEST, "Census not found!"));
    }

    //TODO: getcensusfhir - No Idea About CensusFhir
    /*
    @GetMapping("/getcensusfhir")
    public ResponseEntity<?> getCensusfhir(){

    }*/

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getDetails(@PathVariable(value = "id") String id){
        Residents resident = residentsRepository.findByResidentId(id);
        if(resident!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, null, resident, null));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "Resident not found!"));
    }

    @GetMapping("/location")
    public ResponseEntity<?> getLocation(){
        List<CensusMap> censusMap = residentsRepository.getCensusLocation();
        if(censusMap!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new CensusMapResponse(residentsRepository.getTotalResident(), censusMap));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "Resident not found!"));
    }

    @GetMapping("/chart")
    public ResponseEntity<?> getCensusChart() throws Exception {
        try {
            List<CensusChartList> list = residentsRepository.getAllChart();
            List<String> x = new ArrayList<>();
            List<String> y = new ArrayList<>();
            for (CensusChartList censusChart : list) {
                x.add(censusChart.get_id());
                y.add(censusChart.getDate());
            }
            CensusChart chart = new CensusChart(list.size(), list, x, y);
            return ResponseEntity.status(HttpStatus.OK).body(chart);
        } catch (Exception e) {
            throw new Exception(e.getCause());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCensus(@PathVariable(value = "id") String id, @RequestBody Residents resident){
        Residents res = residentsRepository.findByResidentId(id);
        if(res!=null){
            resident.set_id(res.get_id());
            residentsRepository.save(resident);
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Census information updated successfully!"));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "Census not found!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCensus(@PathVariable(value = "id")String id){
        Residents resident = residentsRepository.findByResidentId(id);
        if(resident!=null){
            residentsRepository.delete(resident);
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Census deleted successfully!"));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "Census not found!"));
    }
}
