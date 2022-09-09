package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.database.Patients;
import com.ayata.urldatabase.model.bridge.PatientListResponse;
import com.ayata.urldatabase.model.bridge.ResponseData;
import com.ayata.urldatabase.model.token.Message;
import com.ayata.urldatabase.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class URLPatient {
    private PatientRepository patientRepository;
    @GetMapping("/getPatientList")
    public ResponseEntity<?> getPatientList(@RequestParam int perPage, @RequestParam int currentPage){
        if(currentPage<=0){
            currentPage = 1;
        }
        List<Patients> list = patientRepository.getLimitPatient(perPage, (currentPage - 1) * perPage);
        if( list.size()>0) {
            return ResponseEntity.status(HttpStatus.OK).body(new PatientListResponse(perPage, currentPage, list.size(), list));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message("Patient List not found in database."));
    }

    @GetMapping("/getPatientDetails/{id}")
    public ResponseEntity<?> getPatientList(@PathVariable String id){
        Patients patient = patientRepository.getPatientById(id);
        if(patient != null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData("200", "success", patient));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Patient List not found in database."));
        }
    }
}
