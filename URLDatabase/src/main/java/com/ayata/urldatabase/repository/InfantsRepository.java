package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.Infants;
import com.ayata.urldatabase.model.database.Patients;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InfantsRepository extends MongoRepository<Infants, String> {
    @Aggregation(pipeline = {"{$match: {'infantPhone': {$in: ?0}}}"})
    public List<Infants> matchedPhoneList(List<String> list);

    @Aggregation(pipeline = {
            "{$match: {'user': ?2}}",
            "{$skip: ?1}",
            "{$limit: ?0}"})
    public List<Infants> getLimitInfantByUser(int perPage, int currentPage, String user);

    @Aggregation(pipeline = {"{$match: {'patientId': {$regex: //}}}",
            "{$group: {_id: 1, count: {$sum: 1}}}"})
    public Integer getTotalInfant();
}
