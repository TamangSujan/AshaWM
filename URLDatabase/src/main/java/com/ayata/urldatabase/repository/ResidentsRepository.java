package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResidentsRepository extends MongoRepository<Residents, String> {
    @Aggregation(pipeline = {"{$match: {'residentId': {$nin: ?0}}}"})
    public List<Residents> findAllExceptGivenList(List<String> residentId);

    @Aggregation(pipeline = {"{$match: {'appuser_id': ?0}}"})
    public List<Residents> findAllByUserId(String userId);

    @Aggregation(pipeline = {"{$match: {$and :[{'appuser_id': ?0}, {'resident_id': {$nin: ?1}}]}}"})
    public List<Residents> findAllByUserIdExceptGivenList(String userId, List<String> residentId);

    @Aggregation(pipeline = {"{$match: {'residentId': ?0}}"})
    public Residents findByResidentId(String residentId);
}
