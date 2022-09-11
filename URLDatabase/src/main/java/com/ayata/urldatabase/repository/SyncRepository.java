package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.SyncHistories;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SyncRepository extends MongoRepository<SyncHistories, String> {
    @Aggregation(pipeline = "{$match: {'user': ?0}}")
    public List<SyncHistories> getByUserId(String id);
}
