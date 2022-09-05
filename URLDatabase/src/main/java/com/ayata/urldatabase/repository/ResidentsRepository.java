package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.Residents;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResidentsRepository extends MongoRepository<Residents, String> {
    @Aggregation(pipeline = {"{$match: {'residentId': {$nin: ?0}}}"})
    public List<Residents> findAllExceptGivenList(List<String> residentId);
}
