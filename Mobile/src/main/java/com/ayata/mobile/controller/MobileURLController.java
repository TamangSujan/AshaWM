package com.ayata.mobile.controller;

import com.ayata.mobile.database.MobileURLDatabase;
import com.ayata.mobile.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/mobile")
public class MobileURLController {
    private MobileURLDatabase database;

    @GetMapping("/getPerson")
    public Person getPerson(){
        return database.getPerson();
    }

    @PostMapping("/checkCensus")
    public ResponseEntity<?> checkCensus(@RequestBody List<String> list, HttpServletRequest request){
        return database.getModelList(list, request);
    }

    @PostMapping("/checkInfantVisit")
    public ResponseEntity<?> checkInfantVisit(@RequestBody List<String> list, HttpServletRequest request){
        return database.getInfantVisitList(list, request);
    }
}
