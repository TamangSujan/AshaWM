package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.database.SyncHistories;
import com.ayata.urldatabase.repository.SyncRepository;
import com.ayata.urldatabase.static_files.StatusCode;
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
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Data found under user id", null, list));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "No data found under user id"));
    }
}
