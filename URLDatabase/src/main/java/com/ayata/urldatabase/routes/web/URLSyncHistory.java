package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.ResponseDetails;
import com.ayata.urldatabase.model.database.SyncHistories;
import com.ayata.urldatabase.repository.SyncRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/web")
public class URLSyncHistory {
    private SyncRepository syncRepository;
    @GetMapping("/checkSyncHistory/{app_user_id}")
    public ResponseEntity<?> checkSyncHistory(@PathVariable(name = "app_user_id") String id){
        List<SyncHistories> list = syncRepository.getByUserId(id);
        if(list!=null && list.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDetails(200, "Success", "Data found under user id", list));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDetails(400, "Failure", "No data found under user id", ""));
    }
}
