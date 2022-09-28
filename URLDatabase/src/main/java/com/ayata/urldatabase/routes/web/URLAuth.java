package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.controller.AuthController;
import com.ayata.urldatabase.model.bridge.*;
import com.ayata.urldatabase.model.bridge.Response.DoctorResponse;
import com.ayata.urldatabase.model.database.Doctors;
import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.model.token.UsernamePassword;
import com.ayata.urldatabase.repository.DoctorRepository;
import com.ayata.urldatabase.repository.UserRepository;
import com.ayata.urldatabase.security.Jwt;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/web")
public class URLAuth {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private static Logger log = LogManager.getLogger(URLAuth.class);
    private BCryptPasswordEncoder encoder;
    private AuthController authController;
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UsernamePassword usernamePassword){
        log.info("REQUEST: Login");
        if(usernamePassword.getPhone().equals("") || usernamePassword.getPassword().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "Field Should Not Be Empty"));
        }
        Optional<Doctors> doctor = doctorRepository.findDoctorByPhone(usernamePassword.getPhone());
        if(!doctor.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "User Not Found"));
        }
        if(encoder.matches(usernamePassword.getPassword(), doctor.get().getPassword())){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePassword.getPhone(), usernamePassword.getPassword()));
            String access_token = Jwt.getAccessToken(usernamePassword.getPhone(), 15, "/api/v2/web/login", false);
            //TODO: IP hasn't been returnedDoc
            DoctorResponse doctorResponse = new DoctorResponse(doctor.get().getDoc_id(), doctor.get().getName(), doctor.get().getPhone(), access_token, "");
            log.info("SUCCESS: Login");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "Success", "Doctor logged in successfully", doctorResponse));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "Invalid Credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsernamePassword usernamePassword){
        log.info("REQUEST: Register");
        Optional<Doctors> doctor = doctorRepository.findDoctorByPhone(usernamePassword.getPhone());
        if(doctor.isPresent()){
            log.error("ERROR: Register - Phone already registered!");
            throw new IllegalStateException("Phone already registered!");
        }else{
            Doctors doc = new Doctors();
            doc.setPhone(usernamePassword.getPhone());
            doc.setPassword(encoder.encode(usernamePassword.getPassword()));
            int id = doctorRepository.getDoctorsCount();
            doc.setDoc_id(id);
            doctorRepository.save(doc);
            log.info("SUCCESS: Doctor registered!");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "success", "Doctor registered successfully!", "{}"));
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword user){
        String message = authController.forgotPassword(user.getUsername(), user.getPassword(), user.getConfirmPassword());
        if(!message.equals("ok")){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "Password Changed!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("400", "Failure", message));
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateUser(@RequestBody UpdateProfile updateProfile){
        log.info("REQUEST: Update Profile");
        Optional<Users> user = userRepository.findByChwIdWeb(updateProfile.getChw_id());
        if(user.isPresent()){
            authController.updateProfile(updateProfile, user.get());
        }else{
            log.error("ERR: User doesn't exists!");
            throw new IllegalStateException("User doesn't exist!");
        }
        log.info("SUCCESS: Profile Updated!");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "success", "Successfully Updated!"));
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody Phone phone){
        Optional<Users> user = userRepository.findByPhoneWeb(phone.getPhone());
        if(user.isPresent()){
            authController.removeUser(user.get());
        }else{
            throw new IllegalStateException("User doesn't exists!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "success", "Successfully Deleted!"));
    }

}
