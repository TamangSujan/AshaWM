package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.VisitListsSafe;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VisitListsSafeRepository extends MongoRepository<VisitListsSafe, String> {

    @Aggregation(pipeline = "{$match: {'visit.visit_category': 'SAFE_MOTHERHOOD'}}")
    public List<VisitListsSafe> getSafeMotherHood();
}
