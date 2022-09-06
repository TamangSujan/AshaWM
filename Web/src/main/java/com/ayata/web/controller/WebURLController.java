package com.ayata.web.controller;

import com.ayata.web.model.Message;
import com.ayata.web.model.ResponseMessage;
import com.ayata.web.model.UsernamePassword;
import com.ayata.web.model.UsernameToken;
import com.ayata.web.path.ApiPath;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
public class WebURLController {
    private RestTemplate restTemplate;
    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody UsernamePassword usernamePassword){
        UsernameToken usernameToken = restTemplate.postForObject(ApiPath.loginUser, usernamePassword, UsernameToken.class);
        return ResponseEntity.status(HttpStatus.OK).body(usernameToken);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UsernamePassword usernamePassword){
        ResponseMessage responseMessage = restTemplate.postForObject(ApiPath.addUser, usernamePassword, ResponseMessage.class);
        return ResponseEntity.status(Integer.parseInt(responseMessage.getCode())).body(responseMessage);
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTest(HttpServletRequest request){
        HttpHeaders headers = getAuthorizedJSONHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Message message = restTemplate.exchange("http://ASHADATABASE/api/asha/database/test", HttpMethod.GET, entity, Message.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/getCHWList")
    public ResponseEntity<?> getChwList(@RequestParam int perPage, @RequestParam int currentPage, HttpServletRequest request){
        HttpHeaders headers = getAuthorizedJSONHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Object object = restTemplate.exchange(ApiPath.getChwList+"?perPage={perPage}&currentPage={currentPage}", HttpMethod.GET, entity, Object.class, perPage, currentPage).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @GetMapping("/getVisitListByCHW/{id}")
    public ResponseEntity<?> getChwList(@PathVariable(name = "id") String id, HttpServletRequest request){
        HttpHeaders headers = getAuthorizedJSONHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Object object = restTemplate.exchange(ApiPath.getVisitListByChw+"/{id}", HttpMethod.GET, entity, Object.class, id).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @PostMapping("/addCensus")
    public ResponseEntity<?> addCensus(@RequestBody Object residents, HttpServletRequest request){
        HttpHeaders headers = getAuthorizedJSONHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<?> entity = new HttpEntity<>(residents, headers);
        ResponseMessage message = restTemplate.exchange(ApiPath.addCensus, HttpMethod.POST, entity, ResponseMessage.class).getBody();
        return ResponseEntity.status(Integer.parseInt(message.getCode())).body(message);
    }

    @PostMapping("/checkCensus")
    public ResponseEntity<?> checkCensus(@RequestBody List<String> residents, HttpServletRequest request) {
        HttpHeaders headers = getAuthorizedJSONHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<?> entity = new HttpEntity<>(residents, headers);
        Object object = restTemplate.exchange(ApiPath.checkCensus, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @GetMapping("/getPatientList")
    public ResponseEntity<?> getPatientList(@RequestParam int perPage, @RequestParam int currentPage, HttpServletRequest request){
        HttpHeaders headers = getAuthorizedJSONHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Object object = restTemplate.exchange(ApiPath.getPatientList+"?perPage={perPage}&currentPage={currentPage}", HttpMethod.GET, entity, Object.class, perPage, currentPage).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @GetMapping("/getPatientDetails/{id}")
    public ResponseEntity<?> getPatientList(@PathVariable(name="id") String id, HttpServletRequest request){
        HttpHeaders headers = getAuthorizedJSONHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Object object = restTemplate.exchange(ApiPath.getPatientDetails+"/{id}", HttpMethod.GET, entity, Object.class, id).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    private HttpHeaders getAuthorizedJSONHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
