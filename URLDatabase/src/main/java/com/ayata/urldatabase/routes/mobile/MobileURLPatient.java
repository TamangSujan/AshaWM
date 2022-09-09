package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.database.InfantVisits;
import com.ayata.urldatabase.model.database.Visits;
import com.ayata.urldatabase.repository.VisitsRepository;
import com.ayata.urldatabase.static_methods.Library;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/mobile")
public class MobileURLPatient {
    private VisitsRepository visitsRepository;
    @PostMapping("/checkVisit")
    public ResponseEntity<?> checkVisits(@RequestBody List<String> list){
        String user = Library.splitAndGetFirst(list.get(0), "_");
        List<Visits> visits = visitsRepository.getVisitsExceptGivenList(user, list);
        return ResponseEntity.status(HttpStatus.OK).body(visits);
    }
}
