package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.Patients;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PatientRepository extends MongoRepository<Patients, String> {
    @Aggregation(pipeline = "{$match: {'patientId': ?0}}")
    public Patients getPatientById(String id);

    @Aggregation(pipeline = "{$match: {'patientId': {$in: ?0}}}")
    public List<Patients> getArrayPatients(List<String> list);
}
