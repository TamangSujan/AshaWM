package com.ayata.web.controller;

import com.ayata.web.model.Message;
import com.ayata.web.model.UsernamePassword;
import com.ayata.web.model.UsernameToken;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class URLController {
    private RestTemplate restTemplate;
    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody UsernamePassword usernamePassword){
        UsernameToken usernameToken = restTemplate.postForObject("http://ASHADATABASE/api/asha/database/loginUser", usernamePassword, UsernameToken.class);
        return ResponseEntity.status(HttpStatus.OK).body(usernameToken);
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTest(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Message message = restTemplate.exchange("http://ASHADATABASE/api/asha/database/test", HttpMethod.GET, entity, Message.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/getChwList")
    public ResponseEntity<?> getChwList(@RequestParam int perPage, @RequestParam int currentPage, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Object object = restTemplate.exchange("http://ASHADATABASE/api/asha/database/getCHWList?perPage={perPage}&currentPage={currentPage}", HttpMethod.GET, entity, Object.class, perPage, currentPage).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }
}
