package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.controller.AuthController;
import com.ayata.urldatabase.model.bridge.ForgotPassword;
import com.ayata.urldatabase.model.bridge.Phone;
import com.ayata.urldatabase.model.bridge.UpdateProfile;
import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.token.UsernamePassword;
import com.ayata.urldatabase.model.token.UsernameToken;
import com.ayata.urldatabase.repository.UserRepository;
import com.ayata.urldatabase.security.Jwt;
import com.ayata.urldatabase.security.JwtUser;
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
@RequestMapping("/api")
public class URLAuth {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private AuthController authController;
    private BCryptPasswordEncoder encoder;
    private JwtUser userDetails;
    private static Logger log = LogManager.getLogger(URLAuth.class);
    @PostMapping("/loginUser")
    public ResponseEntity loginUser(@RequestBody UsernamePassword usernamePassword){
        if(usernamePassword.getPhone().equals("") || usernamePassword.getPassword().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "Field Should Not Be Empty"));
        }
        Users dbUser = userRepository.findByPhone(usernamePassword.getPhone());
        if(dbUser==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "User Not Found"));
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePassword.getPhone(), usernamePassword.getPassword()));
        String access_token = Jwt.getAccessToken(usernamePassword.getPhone(), 60*8, "/api/loginUser", false);
        return ResponseEntity.status(HttpStatus.OK).body(new UsernameToken(dbUser.getChw_id(), dbUser.getChw_name(), dbUser.getChw_gender(), dbUser.getChw_dob(), dbUser.getChw_address(), dbUser.getChw_designation(), dbUser.getImage(), access_token));
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UsernamePassword usernamePassword){
        String message = authController.register(usernamePassword.getPhone(), usernamePassword.getPassword());
        if(message.equals("ok")){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "User Created"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("400", "Failure", message));
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
