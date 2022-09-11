package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.controller.AuthController;
import com.ayata.urldatabase.model.bridge.ForgotPassword;
import com.ayata.urldatabase.model.bridge.ResponseDetailsV2;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.bridge.UpdateProfile;
import com.ayata.urldatabase.model.database.Users;
import com.ayata.urldatabase.model.token.UsernamePassword;
import com.ayata.urldatabase.model.token.UsernameToken;
import com.ayata.urldatabase.repository.UserRepository;
import com.ayata.urldatabase.security.Jwt;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/mobile")
@AllArgsConstructor
public class MobileURLAuth {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private AuthController authController;

    @PostMapping(value = "/loginUser", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity loginUser(UsernamePassword usernamePassword){
        if(usernamePassword.getPhone().equals("") || usernamePassword.getPassword().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "Field Should Not Be Empty"));
        }
        Users dbUser = userRepository.findByPhone(usernamePassword.getPhone());
        if(dbUser==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("400", "Failure", "User Not Found"));
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePassword.getPhone(), usernamePassword.getPassword()));
        String access_token = Jwt.getAccessToken(usernamePassword.getPhone(), 60*8, "/api/loginUser");
        ResponseDetailsV2 response = new ResponseDetailsV2("200", "success", new UsernameToken(dbUser.getChw_id(), dbUser.getChw_name(), dbUser.getChw_gender(), dbUser.getChw_dob(), access_token));
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
        Users user = userRepository.findByChwId(updateProfile.getChw_id());
        user.setChw_name(updateProfile.getChw_name());
        user.setChw_address(updateProfile.getChw_address());
        user.setChw_dob(updateProfile.getChw_dob());
        user.setChw_designation(updateProfile.getChw_designation());
        user.setChw_gender(updateProfile.getChw_gender());
        user.setImage(updateProfile.getChw_image());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
