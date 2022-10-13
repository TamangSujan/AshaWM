package com.ayata.urldatabase.services;

import com.ayata.urldatabase.model.database.Infants;
import com.ayata.urldatabase.repository.InfantVisitListsRepository;
import com.ayata.urldatabase.repository.InfantsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InfantService {
    private InfantVisitListsRepository infantVisitListsRepository;
    private InfantsRepository infantsRepository;

    public Integer getInfantCount(String chwId){
        Integer total = infantsRepository.getTotalInfantById(chwId);
        if(total!=null){
            return total;
        }
        return 0;
    }

    public Object getRiskInfants(){
        return infantVisitListsRepository.getRiskInfants();
    }
}
