package com.ayata.mobile.database;

import com.ayata.mobile.model.Person;
import com.ayata.mobile.path.ApiAsha;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@AllArgsConstructor
public class MobileURLDatabase {
    private RestTemplate restTemplate;

    public Person getPerson(){
        return restTemplate.getForObject("http://ASHADATABASE/getPerson", Person.class);
    }

    public ResponseEntity<?> getModelList(List<String> list, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpEntity entity = new HttpEntity(list, getAuthorizedJSONHeader(token));
        Object object = restTemplate.exchange(ApiAsha.checkCensus, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    public ResponseEntity<?> getInfantVisitList(List<String> list, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpEntity entity = new HttpEntity(list, getAuthorizedJSONHeader(token));
        Object object = restTemplate.exchange(ApiAsha.checkInfantVisit, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    private HttpHeaders getAuthorizedJSONHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
