package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.InfantVisits;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InfantVisitsRepository extends MongoRepository<InfantVisits, String> {

    /*
    @Aggregation(pipeline = {"{$all: }"})
    List<InfantVisits> getInfantVisitLists();
    */
}
