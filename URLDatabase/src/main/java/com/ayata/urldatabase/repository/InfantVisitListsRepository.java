package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.InfantVisitLists;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InfantVisitListsRepository extends MongoRepository<InfantVisitLists, String> {
    @Aggregation(pipeline = {"{$match: {'infantVisit.iRisk.does_have_risk': ?0}}"})
    List<InfantVisitLists> getInfantWithRisk(String risk);

}
