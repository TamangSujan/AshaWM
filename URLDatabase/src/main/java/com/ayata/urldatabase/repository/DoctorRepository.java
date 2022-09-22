package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.database.Doctors;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DoctorRepository extends MongoRepository<Doctors, String> {
    @Aggregation(pipeline = "{$match: {'phone': ?0}}")
    public Optional<Doctors> findDoctorByPhone(String phone);

    @Aggregation(pipeline = "{$count: 'doc_id'}")
    public Integer getDoctorsCount();
}
