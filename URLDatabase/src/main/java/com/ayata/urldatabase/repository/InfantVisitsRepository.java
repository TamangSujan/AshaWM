package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.InfantVisits;
import com.ayata.urldatabase.model.database.Visits;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InfantVisitsRepository extends MongoRepository<InfantVisits, String> {

    @Aggregation(pipeline = {"{$match: {$and: [{'user': ?0} ,{'AppUserList.modelInfants.modelVisitList.visit_id' :{$nin : ?1}}]}}"})
    List<InfantVisits> getInfantVisitExceptGivenList(String user, List<String> list);

    @Aggregation(pipeline = {"{$match: {user: ?0}}",
            "{$limit: 1}"})
    InfantVisits getVisitByUserId(String user);

}
