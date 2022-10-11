package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.VisitLists;
import com.ayata.urldatabase.model.database.Visits;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VisitListsRepository extends MongoRepository<VisitLists, String> {

    @Aggregation(pipeline = "{$match: {'visit.visit_category': 'SAFE_MOTHERHOOD'}}")
    public List<VisitLists> getSafeMotherHood();

    @Aggregation(pipeline = "{$match: {'visit.visit_category': 'CHRONIC_DISEASE'}}")
    public List<VisitLists> getChronicCase();

    @Aggregation(pipeline = "{$match: {'user_id': ?0}}")
    public List<VisitLists> getVisitListByUserId(String id);

    @Aggregation(pipeline = {"{$match: {$and: [{'user_id': ?0},{'visit.visit_id' :{$nin : ?1}}]}}"})
    List<VisitLists> getVisitsExceptGivenList(String user, List<String> list);

    @Aggregation(pipeline = {"{$match: {$and: [{'user_id': ?0},{'visit.visit_id' :{$in : ?1}}]}}"})
    List<VisitLists> getVisitsOfGivenList(String user, List<String> list);

    @Aggregation(pipeline = {"{$match: {$and: [{'user_id': ?0},{'patientId' : ?1}]}}"})
    List<VisitLists> getVisitsList(String user, String patientId);

    @Aggregation(pipeline = "{$match: {'user_id': ?0}}")
    public VisitLists findByUserId(String user);

    @Aggregation(pipeline = {"{$match: {'visit.visit_category': ?0}}",
            "{$count: ?0}"})
    public Integer getCounts(String category);
}
