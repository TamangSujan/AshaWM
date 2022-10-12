package com.ayata.urldatabase.services;

import com.ayata.urldatabase.model.bridge.TrimesterCount;
import com.ayata.urldatabase.repository.VisitListsRepository;
import com.ayata.urldatabase.routes.web.misc.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitService {

    private final int WEEK = 7*24*60*60*1000;
    private final int MONTH = 30*24*60*60*1000;
    private VisitListsRepository visitListsRepository;
    public Integer getPregnantCountByChwId(String chwId){
        Integer count = visitListsRepository.getPregnancyCount(chwId);
        if(count!=null){
            return count;
        }
        return 0;
    }

    public TrimesterCount getTrimesterCount(String chwId){
        TrimesterCount trimesterCount = new TrimesterCount(0, 0, 0, 0, 0);
        List<String> trimesterList = visitListsRepository.getTrimesterCount(chwId, WEEK);
        for(String trimester: trimesterList){
            switch (trimester){
                case "trimester1":
                    trimesterCount.setTrimester1(trimesterCount.getTrimester1()+1);
                    break;
                case "trimester2":
                    trimesterCount.setTrimester2(trimesterCount.getTrimester2()+1);
                    break;
                case "trimester3":
                    trimesterCount.setTrimester3(trimesterCount.getTrimester3()+1);
                    break;
                case "trimester4":
                    trimesterCount.setTrimester4(trimesterCount.getTrimester4()+1);
                    break;
                case "finished":
                    trimesterCount.setTrimesterfinished(trimesterCount.getTrimesterfinished()+1);
                    break;
            }
        }
        return trimesterCount;
    }

    public Integer getNewChronicOrPregnancyCountByChwId(Category category, String chwId){
        Integer total = visitListsRepository.getNewChronicOrPregnancyCount(category, chwId, MONTH);
        if(total!=null){
            return total;
        }
        return 0;
    }

    public Integer getNewDeliveryCountByChwId(String chwId){
        Integer total = visitListsRepository.getNewDeliveryCount(chwId, MONTH);
        if(total!=null){
            return total;
        }
        return 0;
    }

    public Integer getRiskPregnancyCount(String chwId){
        Integer total = visitListsRepository.getRiskPregnancyCountByChwId(chwId);
        if(total!=null){
            return total;
        }
        return 0;
    }

    public Integer getComplicateDeliveryCount(String chwId){
        Integer total = visitListsRepository.getComplicationDeliveryCountByChwId(chwId);
        if(total!=null){
            return total;
        }
        return 0;
    }

    public Integer getFollowUpdateCount(String chwId){
        Integer total = visitListsRepository.getFollowUpdateCount(chwId);
        if(total!=null){
            return total;
        }
        return 0;
    }

    public Integer getChronicDiseaseCount(String chwId){
        Integer total = visitListsRepository.getChronicDiseaseCount(chwId);
        if(total!=null){
            return total;
        }
        return 0;
    }

    public Integer getRiskPatientCount(String chwId){
        Integer total = visitListsRepository.getRiskPatientCount(chwId);
        if(total!=null){
            return total;
        }
        return 0;
    }
}
