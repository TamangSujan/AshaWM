package com.ayata.mobile.database;

import com.ayata.mobile.model.ResponseDetails;
import com.ayata.mobile.model.UpdateProfileForm;
import com.ayata.mobile.model.UsernamePassword;
import com.ayata.mobile.path.ApiAsha;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@Service
@AllArgsConstructor
public class MobileURLDatabase {
    private RestTemplate restTemplate;
    public ResponseEntity<?> loginUser(UsernamePassword usernamePassword, HttpServletRequest request){
        HttpHeaders header = new HttpHeaders();
        HttpEntity entity = new HttpEntity(usernamePassword, header);
        ResponseDetails response = restTemplate.exchange(ApiAsha.loginUser, HttpMethod.POST, entity, ResponseDetails.class).getBody();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    public ResponseEntity<?> updateProfile(UpdateProfileForm form, HttpServletRequest request) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("chw_id", form.getChw_id());
        map.add("chw_name", form.getChw_name());
        map.add("chw_address", form.getChw_address());
        map.add("chw_gender", form.getChw_gender());
        map.add("chw_designation", form.getChw_designation());
        map.add("chw_dob", form.getChw_dob());
        map.add("image", form.getImage().getResource());
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity(map, getAuthorizedMultipartHeader(token));
        Object object = restTemplate.exchange(ApiAsha.updateProfile, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    public ResponseEntity<?> checkCensus(List<String> list, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpEntity entity = new HttpEntity(list, getAuthorizedJSONHeader(token));
        Object object = restTemplate.exchange(ApiAsha.checkCensus, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    public ResponseEntity<?> checkVisit(List<String> list, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpEntity entity = new HttpEntity(list, getAuthorizedJSONHeader(token));
        Object object = restTemplate.exchange(ApiAsha.checkVisit, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    public ResponseEntity<?> checkInfantVisit(List<String> list, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpEntity entity = new HttpEntity(list, getAuthorizedJSONHeader(token));
        Object object = restTemplate.exchange(ApiAsha.checkInfantVisit, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    public ResponseEntity<?> checkSyncHistory(String id, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpEntity entity = new HttpEntity(getAuthorizedJSONHeader(token));
        Object object = restTemplate.exchange(ApiAsha.checkSyncHistory+"/"+id, HttpMethod.GET, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    public ResponseEntity<?> addCensus(Object censusRoot, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpEntity entity = new HttpEntity(censusRoot, getAuthorizedJSONHeader(token));
        Object object = restTemplate.exchange(ApiAsha.addCensus, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    /*
        For Multipart Form LinkedMultiValueMap is necessary and to transmit
        We need MultipartFile and on map we need to access getResource().
     */
    public ResponseEntity<?> addVisit(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("json", request.getParameter("json"));
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity(map, getAuthorizedMultipartHeader(token));
        Object object = restTemplate.exchange(ApiAsha.addVisit, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    public ResponseEntity<?> addInfantVisit(Object appUserList, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpEntity entity = new HttpEntity(appUserList, getAuthorizedJSONHeader(token));
        Object object = restTemplate.exchange(ApiAsha.addInfantVisit, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    public ResponseEntity<?> addSyncHistory(Object sync, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpEntity entity = new HttpEntity(sync, getAuthorizedJSONHeader(token));
        Object object = restTemplate.exchange(ApiAsha.addSyncHistory, HttpMethod.POST, entity, Object.class).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    private HttpHeaders getAuthorizedJSONHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpHeaders getAuthorizedMultipartHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(token);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    private HttpHeaders getTokenHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(token);
        return headers;
    }
}
