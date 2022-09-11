package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.Infants;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InfantsRepository extends MongoRepository<Infants, String> {
    @Aggregation(pipeline = {"{$match: {'infantPhone': {$in: ?0}}}"})
    public List<Infants> matchedPhoneList(List<String> list);
}
