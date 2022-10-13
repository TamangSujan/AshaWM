package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.InfantVisitLists;
import com.ayata.urldatabase.model.database.VisitLists;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InfantVisitListsRepository extends MongoRepository<InfantVisitLists, String> {
    @Aggregation(pipeline = {"{$match: {'infantVisit.iRisk.does_have_risk': ?0}}"})
    List<InfantVisitLists> getInfantWithRisk(String risk);

    @Aggregation(pipeline = {"{$match: {$and: [{'user_id': ?0},{'infantId' : ?1}]}}"})
    List<InfantVisitLists> getInfantVisitsList(String user, String infantId);

    @Aggregation(pipeline = {"{$match: {'infantVisit.iRisk.does_have_risk': 'YES'}}",
                            "{$group: {_id: {user_id: '$user_id', risk: '$infantVisit.iRisk.riskSigns', infantId: '$infantId'}}}"})
    public List<Object> getRiskInfants();
}
