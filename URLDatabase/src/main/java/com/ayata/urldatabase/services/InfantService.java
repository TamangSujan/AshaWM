package com.ayata.urldatabase.services;

import com.ayata.urldatabase.repository.InfantsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InfantService {
    private InfantsRepository infantsRepository;

    public Integer getInfantCount(String chwId){
        Integer total = infantsRepository.getTotalInfantById(chwId);
        if(total!=null){
            return total;
        }
        return 0;
    }
}
