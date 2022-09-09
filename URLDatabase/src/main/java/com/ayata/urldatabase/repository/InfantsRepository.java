package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.Infants;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InfantsRepository extends MongoRepository<Infants, String> {
}
