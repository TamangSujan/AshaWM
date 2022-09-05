package com.ayata.mobile.controller;

import com.ayata.mobile.database.URLDatabase;
import com.ayata.mobile.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class URLController {
    private URLDatabase database;

    @GetMapping("/getPerson")
    public Person getPerson(){
        return database.getPerson();
    }
}
