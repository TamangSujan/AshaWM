package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.CheckInfantVisitResponse;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.database.AppUserList;
import com.ayata.urldatabase.model.database.InfantVisits;
import com.ayata.urldatabase.repository.InfantVisitsRepository;
import com.ayata.urldatabase.static_methods.Library;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/mobile")
public class MobileURLInfant {
    private InfantVisitsRepository infantVisitsRepository;
    @PostMapping("/checkInfantVisit")
    public ResponseEntity<?> checkInfantVisit(@RequestBody List<String> list){
        String user = Library.splitAndGetFirst(list.get(0), "_");
        return ResponseEntity.status(HttpStatus.OK).body(infantVisitsRepository.getInfantVisitExceptGivenList(user, list).get(0).getAppUserList());
    }
}
