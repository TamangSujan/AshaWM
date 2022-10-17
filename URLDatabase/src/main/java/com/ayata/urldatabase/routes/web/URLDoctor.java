package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.*;
import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.database.Doctors;
import com.ayata.urldatabase.repository.DoctorRepository;
import com.ayata.urldatabase.security.Jwt;
import com.ayata.urldatabase.static_files.StatusCode;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/web/Doctor")
public class URLDoctor {
    private DoctorRepository doctorRepository;
    private BCryptPasswordEncoder encoder;
    private static Logger log = LogManager.getLogger(URLDoctor.class);
    @PutMapping("/details")
    public ResponseEntity<?> updateDetails(HttpServletRequest request, @RequestBody DoctorDetails details) throws ServletException, IOException {
        log.info("REQUEST: Doctor details");
        String phone = Jwt.getPhone(request);
        Optional<Doctors> doctorOptional = doctorRepository.findDoctorByPhone(phone);
        if(doctorOptional.isPresent()){
            Doctors doctor = doctorOptional.get();
            doctor.setName(details.getName());
            doctor.setBio(details.getBio());
            doctor.setAddress(details.getAddress());
            doctorRepository.save(doctor);
            DoctorBasicDetails basicDetails = new DoctorBasicDetails(doctor.getDoc_id(), doctor.getName(), doctor.getPhone(), doctor.getAddress(), doctor.getBio());
            log.info("SUCCESS: Doctor updated successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Details updated successfully", null, basicDetails));
        }
        log.error("ERROR: Doctor update error!");
        return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.BAD_REQUEST, "Error on updating!"));
    }

    @GetMapping("/details")
    public ResponseEntity<?> getDetails(HttpServletRequest request) throws ServletException, IOException {
        log.info("REQUEST: Doctor details");
        String phone = Jwt.getPhone(request);
        Optional<Doctors> doctorOptional = doctorRepository.findDoctorByPhone(phone);
        if(doctorOptional.isPresent()) {
            Doctors doctor = doctorOptional.get();
            DoctorBasicDetails basicDetails = new DoctorBasicDetails(doctor.getDoc_id(), doctor.getName(), doctor.getPhone(), doctor.getAddress(), doctor.getBio());
            log.info("SUCCESS: Doctor details fetched successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Details updated successfully", null, basicDetails));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FinalResponse(StatusCode.BAD_REQUEST,"Error while updating doctor"));
    }

   @PutMapping("/changepassword")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @RequestBody ForgotPasswordWeb forgotPassword){
        String phone = Jwt.getPhone(request);
        Optional<Doctors> doctorsOptional = doctorRepository.findDoctorByPhone(phone);
        if(doctorsOptional.isPresent()){
            Doctors doctor = doctorsOptional.get();
            if(encoder.matches(forgotPassword.getOld_password(), doctor.getPassword()) && forgotPassword.getNew_password().equals(forgotPassword.getConfirm_password())){
                doctor.setPassword(encoder.encode(forgotPassword.getNew_password()));
                doctorRepository.save(doctor);
                DoctorBasicDetails basicDetails = new DoctorBasicDetails(doctor.getDoc_id(), doctor.getName(), doctor.getPhone(), doctor.getAddress(), doctor.getBio());
                return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Password updated successfully.", null, basicDetails));
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FinalResponse(StatusCode.INTERNAL_SERVER_ERROR, "Error on updating password"));
   }
}
