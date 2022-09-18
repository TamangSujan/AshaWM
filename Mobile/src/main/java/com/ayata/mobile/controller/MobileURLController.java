package com.ayata.mobile.controller;

import com.ayata.mobile.database.MobileURLDatabase;
import com.ayata.mobile.model.Person;
import com.ayata.mobile.model.UpdateProfileForm;
import com.ayata.mobile.model.UsernamePassword;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/mobile")
public class MobileURLController {
    private MobileURLDatabase database;

    @PostMapping(value = "/loginUser", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> loginUser(UsernamePassword usernamePassword, HttpServletRequest request){
        return database.loginUser(usernamePassword, request);
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/updateProfile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProfile(@ModelAttribute UpdateProfileForm form, HttpServletRequest request) throws ServletException, IOException {
        return database.updateProfile(form, request);
    }

    @PostMapping("/checkCensus")
    public ResponseEntity<?> checkCensus(@RequestBody List<String> list, HttpServletRequest request){
        return database.checkCensus(list, request);
    }

    @PostMapping("/checkVisit")
    public ResponseEntity<?> checkVisit(@RequestBody List<String> list, HttpServletRequest request){
        return database.checkVisit(list, request);
    }

    @PostMapping("/checkInfantVisit")
    public ResponseEntity<?> checkInfantVisit(@RequestBody List<String> list, HttpServletRequest request){
        return database.checkInfantVisit(list, request);
    }

    @GetMapping("/checkSyncHistory/{id}")
    public ResponseEntity<?> checkSyncHistory(@PathVariable(name = "id") String id, HttpServletRequest request){
        return database.checkSyncHistory(id, request);
    }

    @PostMapping("/addCensus")
    public ResponseEntity<?> addCensus(@RequestBody Object censusRoot, HttpServletRequest request){
        return database.addCensus(censusRoot, request);
    }
    @PostMapping(value = "/addVisit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addVisit(HttpServletRequest request){
        return database.addVisit(request);
    }

    @PostMapping("/addInfantVisit")
    public ResponseEntity<?> addInfantVisit(@RequestBody Object appUserList, HttpServletRequest request){
        return database.addInfantVisit(appUserList, request);
    }

    @PostMapping("/addSyncHistory")
    public ResponseEntity<?> addSyncHistory(@RequestBody Object sync, HttpServletRequest request){
        return database.addSyncHistory(sync, request);
    }
}
