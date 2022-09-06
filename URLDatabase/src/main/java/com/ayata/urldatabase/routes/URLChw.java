package com.ayata.urldatabase.routes;

import com.ayata.urldatabase.model.Users;
import com.ayata.urldatabase.model.VisitLists;
import com.ayata.urldatabase.model.bridge.ChwListResponse;
import com.ayata.urldatabase.model.bridge.ResponseData;
import com.ayata.urldatabase.model.token.Message;
import com.ayata.urldatabase.repository.ChwRepository;
import com.ayata.urldatabase.repository.VisitListsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class URLChw {
    private ChwRepository chwRepository;
    private VisitListsRepository visitListsRepository;
    @GetMapping("/getCHWList")
    public ResponseEntity<?> getChwList(@RequestParam int perPage, @RequestParam int currentPage){
        if(currentPage<=0){
            currentPage = 1;
        }
        List<Users> list = chwRepository.getLimitPageUsers(perPage, (currentPage-1)*perPage);
        if(list.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new ChwListResponse(perPage, currentPage, list.size(), list));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Message("chwList not found in database."));
        }
    }

    @GetMapping("/getVisitListByCHW/{id}")
    public ResponseEntity<?> getChwListByUserId(@PathVariable(name = "id") String id){
        System.out.println(id);
        List<VisitLists> list = visitListsRepository.getVisitListByUserId(id);
        if(list.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData("200", "success", list));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Message("chwList not found in database."));
        }
    }
}
