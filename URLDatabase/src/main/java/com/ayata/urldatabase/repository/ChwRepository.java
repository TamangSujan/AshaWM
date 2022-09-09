package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.Users;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChwRepository extends MongoRepository<Users, String> {
    @Aggregation(pipeline = {
            "{$match: {'chw_id': {$gt: 0}}}",
            "{$skip: ?1}",
            "{$limit: ?0}"})
    public List<Users> getLimitPageUsers(int perPage, int currentPage);
}
