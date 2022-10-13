package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.bridge.WebStaff;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WebChwRepository extends MongoRepository<WebStaff, String> {
    @Aggregation(pipeline = "{$match: {'chw_id': ?0}}")
    public Optional<WebStaff> getByChwId(Integer chwId);
}
