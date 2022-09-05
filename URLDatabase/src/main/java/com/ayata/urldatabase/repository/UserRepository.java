package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.Users;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
    @Aggregation(pipeline = "{$match : {'phone': ?0}}")
    public Users getUser(String phone);
}
