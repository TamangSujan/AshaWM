package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.VisitLists;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VisitListsRepository extends MongoRepository<VisitLists, String> {

    @Aggregation(pipeline = "{$match: {'visit.visit_category': 'SAFE_MOTHERHOOD'}}")
    public List<VisitLists> getSafeMotherHood();

    @Aggregation(pipeline = "{$match: {'visit.visit_category': 'CHRONIC_DISEASE'}}")
    public List<VisitLists> getChronicCase();
}
