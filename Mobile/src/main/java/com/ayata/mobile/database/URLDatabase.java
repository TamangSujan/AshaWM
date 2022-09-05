package com.ayata.mobile.database;

import com.ayata.mobile.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class URLDatabase {
    private RestTemplate restTemplate;

    public Person getPerson(){
        return restTemplate.getForObject("http://ASHADATABASE/getPerson", Person.class);
    }
}
