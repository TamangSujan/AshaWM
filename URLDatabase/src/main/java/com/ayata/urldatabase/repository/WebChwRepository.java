package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.bridge.WebStaff;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WebChwRepository extends MongoRepository<WebStaff, String> {

}
