package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.bridge.CensusChart;
import com.ayata.urldatabase.model.bridge.CensusChartList;
import com.ayata.urldatabase.model.bridge.CensusMap;
import com.ayata.urldatabase.model.bridge.PatientChartList;
import com.ayata.urldatabase.model.database.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResidentsRepository extends MongoRepository<Residents, String> {
    @Aggregation(pipeline = {"{$match: {'residentId': {$nin: ?0}}}"})
    public List<Residents> findAllExceptGivenList(List<String> residentId);

    @Aggregation(pipeline = {"{$match: {'appuser_id': ?0}}"})
    public List<Residents> findAllByUserId(String userId);

    @Aggregation(pipeline = {"{$match: {$and :[{'appuser_id': ?0}, {'resident_id': {$nin: ?1}}]}}"})
    public List<Residents> findAllByUserIdExceptGivenList(String userId, List<String> residentId);

    @Aggregation(pipeline = {"{$match: {'residentId': ?0}}"})
    public Residents findByResidentId(String residentId);

    @Aggregation(pipeline = {
            "{$match: {'user': ?2}}",
            "{$skip: ?1}",
            "{$limit: ?0}"})
    public List<Residents> getLimitResidentByUser(int perPage, int currentPage, String user);

    @Aggregation(pipeline = {
            "{$sort: {residentId: -1}}",
            "{$skip: ?1}",
            "{$limit: ?0}"})
    public List<Residents> getLimitResident(int perPage, int currentPage);

//    @Aggregation(pipeline = {"{$match: {'patientId': {$regex: //}}}",
//            "{$group: {_id: 1, count: {$sum: 1}}}"})
//    public Integer getTotalResident();

    @Aggregation(pipeline = {"{$count: 'residentId'}"})
    public Integer getTotalResident();

    @Aggregation(pipeline = {"{$match: {'residentId': {$regex: //}}}",
            "{$project: {'latitude': '$resident.latitude', 'longitude': '$resident.longitude', 'resident_first_name': '$resident.resident_first_name', 'resident_last_name': '$resident.resident_last_name'}}"})
    public List<CensusMap> getCensusLocation();

    @Aggregation(pipeline = {"{$match: {'enteredDateTime.dateTime.date.year': {$ne: null}}}",
            "{$project: {'_id': $_id, 'date': {$concat: [{$toString: '$enteredDateTime.dateTime.date.year'}, '/'," +
                    "{$toString: '$enteredDateTime.dateTime.date.month'}, '/'," +
                    "{$toString: '$enteredDateTime.dateTime.date.day'}]}}}"})
    public List<CensusChartList> getAllChart();
}
