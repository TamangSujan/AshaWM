package com.ayata.urldatabase.routes;

import com.ayata.urldatabase.controller.AuthController;
import com.ayata.urldatabase.model.Users;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.token.Message;
import com.ayata.urldatabase.model.token.UsernamePassword;
import com.ayata.urldatabase.model.token.UsernameToken;
import com.ayata.urldatabase.repository.UserRepository;
import com.ayata.urldatabase.security.Jwt;
import com.ayata.urldatabase.security.JwtUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class URLAuth {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private AuthController authController;
    private BCryptPasswordEncoder encoder;
    private JwtUser userDetails;
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
        String access_token = Jwt.getAccessToken(usernamePassword.getPhone(), 60*8, "/api/loginUser");
        return ResponseEntity.status(HttpStatus.OK).body(new UsernameToken(usernamePassword.getPhone(), access_token));
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UsernamePassword usernamePassword){
        String message = authController.register(usernamePassword.getPhone(), usernamePassword.getPassword());
        if(message.equals("ok")){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "Success", "User Created"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("400", "Failure", message));
    }

    /*
    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateUser(){

    }
    */
    @GetMapping("/test")
    public ResponseEntity<?> getTest(){
        return ResponseEntity.status(HttpStatus.OK).body(new Message("Am i authenticated and authorized?"));
    }
}
