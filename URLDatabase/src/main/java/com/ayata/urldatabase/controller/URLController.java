package com.ayata.urldatabase.controller;

import com.ayata.urldatabase.model.Users;
import com.ayata.urldatabase.model.bridge.CensusRoot;
import com.ayata.urldatabase.model.bridge.ChwListResponse;
import com.ayata.urldatabase.model.token.Message;
import com.ayata.urldatabase.model.token.UsernamePassword;
import com.ayata.urldatabase.model.token.UsernameToken;
import com.ayata.urldatabase.repository.ChwRepository;
import com.ayata.urldatabase.repository.ResidentsRepository;
import com.ayata.urldatabase.repository.UserRepository;
import com.ayata.urldatabase.security.Jwt;
import com.ayata.urldatabase.security.JwtUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/asha/database/")
public class URLController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private ChwRepository chwRepository;
    private JwtUser userDetails;
    @PostMapping("/loginUser")
    public ResponseEntity loginUser(@RequestBody UsernamePassword usernamePassword){
        System.out.println(usernamePassword.getPhone());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePassword.getPhone(), usernamePassword.getPassword()));
        UserDetails user = userDetails.loadUserByUsername(usernamePassword.getPhone());
        String username = user.getUsername();
        String access_token = Jwt.getAccessToken(username, 60*8, "/api/asha/database/loginUser");
        return ResponseEntity.status(HttpStatus.OK).body(new UsernameToken(username, access_token));
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTest(){
        return ResponseEntity.status(HttpStatus.OK).body(new Message("Am i authenticated and authorized?"));
    }

    @GetMapping("/getCHWList")
    public ResponseEntity<?> getChwList(@RequestParam int perPage, @RequestParam int currentPage){
        if(currentPage<=0){
            currentPage = 1;
        }
        System.out.println(currentPage);
        List<Users> list = chwRepository.getLimitPageUsers(perPage, (currentPage-1)*perPage);
        if(list.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new ChwListResponse(perPage, currentPage, list.size(), list));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Message("chwList not found in database."));
        }
    }
}
