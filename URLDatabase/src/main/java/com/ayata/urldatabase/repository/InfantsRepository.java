package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.Infants;
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

    @Aggregation(pipeline = {
            "{$match: {'infantId': {$regex: //}}}",
            "{$skip: ?1}",
            "{$limit: ?0}"})
    public List<Infants> getLimitInfant(int perPage, int currentPage);

    @Aggregation(pipeline = {"{$match: {'infantId': {$regex: //}}}",
            "{$group: {_id: 1, count: {$sum: 1}}}"})
    public Integer getTotalInfant();

    @Aggregation(pipeline = "{$match: {'infantId': ?0}}")
    public Infants findInfantById(String id);

}
