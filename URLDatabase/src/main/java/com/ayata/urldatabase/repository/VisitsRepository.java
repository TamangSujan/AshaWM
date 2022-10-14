package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.Visits;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VisitsRepository extends MongoRepository<Visits, String> {
    @Aggregation(pipeline = {"{$match: {$and: [{'user': ?0},{'AppUserList.modelPatientList.patientId' :{$nin : ?1}}]}}"})
    List<Visits> getVisitsExceptGivenList(String user, List<String> list);

    @Aggregation(pipeline = {"{$match: {user: ?0}}",
                            "{$limit: 1}"})
    Visits getVisitByUserId(String user);
}
