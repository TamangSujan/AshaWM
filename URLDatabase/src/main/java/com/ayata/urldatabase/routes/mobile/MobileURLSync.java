package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.Response.AddSyncResponse;
import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.database.SyncHistories;
import com.ayata.urldatabase.repository.SyncRepository;
import com.ayata.urldatabase.static_files.StatusCode;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/mobile")
public class MobileURLSync {
    private SyncRepository syncRepository;
    private static final Logger log = LogManager.getLogger(MobileURLSync.class);
    @GetMapping("/checkSyncHistory/{id}")
    public ResponseEntity<?> checkSyncHistory(@PathVariable(name = "id") String id){
        log.info("REQUEST: Check Sync History");
        List<SyncHistories> list = syncRepository.getByUserId(id);
        if(list.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Data found under user "+id, null, list));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new FinalResponse(StatusCode.NO_CONTENT, "No data found under user "+id, null, list));
    }

    @PostMapping("/addSyncHistory")
    public ResponseEntity<?> addSyncHistory(@RequestBody AddSyncResponse sync){
        log.info("REQUEST: Add Sync History");
        SyncHistories history = new SyncHistories();
        history.setId(sync.getId());
        history.setApp_user_id(sync.getApp_user_id());
        history.setTime(sync.getTime());
        history.setDate(sync.getDate());
        syncRepository.save(history);
        log.info("SUCCESS: Adding Sync History");
        return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Synced"));
    }
}
