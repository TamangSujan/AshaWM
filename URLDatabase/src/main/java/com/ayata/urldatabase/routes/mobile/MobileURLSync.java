package com.ayata.urldatabase.routes.mobile;

import com.ayata.urldatabase.model.bridge.AddSyncResponse;
import com.ayata.urldatabase.model.bridge.ResponseDetails;
import com.ayata.urldatabase.model.bridge.ResponseMessage;
import com.ayata.urldatabase.model.database.SyncHistories;
import com.ayata.urldatabase.repository.SyncRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/mobile")
public class MobileURLSync {
    private SyncRepository syncRepository;
    @GetMapping("/checkSyncHistory/{id}")
    public ResponseEntity<?> checkSyncHistory(@PathVariable(name = "id") String id){
        List<SyncHistories> list = syncRepository.getByUserId(id);
        ResponseDetails details;
        if(list.size()>0){
            details = new ResponseDetails(200, "success", "Data found under user "+id, list);
        }else{
            details = new ResponseDetails(200, "success", "No data found under user "+id, list);
        }
        return ResponseEntity.status(HttpStatus.OK).body(details);
    }

    @PostMapping("/addSyncHistory")
    public ResponseEntity<?> addSyncHistory(@RequestBody AddSyncResponse sync){
        SyncHistories history = new SyncHistories();
        history.setId(sync.getId());
        history.setApp_user_id(sync.getApp_user_id());
        history.setTime(sync.getTime());
        history.setDate(sync.getDate());
        syncRepository.save(history);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("200", "success", "Synced"));
    }
}
