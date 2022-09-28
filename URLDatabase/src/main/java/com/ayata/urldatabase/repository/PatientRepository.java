package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.bridge.LocationPatient;
import com.ayata.urldatabase.model.bridge.PatientChartList;
import com.ayata.urldatabase.model.bridge.PatientShortDetails;
import com.ayata.urldatabase.model.database.Patients;
import com.ayata.urldatabase.model.database.Users;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Map;

public interface PatientRepository extends MongoRepository<Patients, String> {
    @Aggregation(pipeline = "{$match: {'patientId': ?0}}")
    public Patients getPatientById(String id);

    @Aggregation(pipeline = "{$match: {'patientId': {$in: ?0}}}")
    public List<Patients> getArrayPatients(List<String> list);

    @Aggregation(pipeline = {
            "{$match: {'patientAge': {$ne: '-1'}}}",
            "{$skip: ?1}",
            "{$limit: ?0}"})
    public List<Patients> getLimitPatient(int perPage, int currentPage);

    @Aggregation(pipeline = {
            "{$match: {'user': ?2}}",
            "{$skip: ?1}",
            "{$limit: ?0}"})
    public List<Patients> getLimitPatientByUser(int perPage, int currentPage, String user);

    @Aggregation(pipeline = {"{$match: {'patientPhone': {$in: ?0}}}"})
    public List<Patients> matchedPhoneList(List<String> patients);

    // Left side use 'variable name' and right side use '$variable name' as $ indicates variable value
    @Aggregation(pipeline = {"{$match: {$and: [{'user': ?0}, {'patientAddedDate': {$ne: null}}] }}",
                                "{$project: {'patientAddedDate': {$substr: ['$patientAddedDate', 0, 10]}}}",
                                "{$group: {_id: '$patientAddedDate', count: {$sum: 1}}}"})
    public List<PatientChartList> getChart(String user);

    @Aggregation(pipeline = {"{$match: {'patientAddedDate': {$ne: null}}}",
            "{$project: {'patientAddedDate': {$substr: ['$patientAddedDate', 0, 10]}}}",
            "{$group: {_id: '$patientAddedDate', count: {$sum: 1}}}"})
    public List<PatientChartList> getAllChart();

    @Aggregation(pipeline = {"{$match: {'patientId': {$regex: //}}}",
                            "{$group: {_id: 1, count: {$sum: 1}}}"})
    public Integer getTotalPatient();

    /* $dateFromString takes date object as a argument but found String on database
    @Aggregation(pipeline = {"{$match: {$and: [{'user': ?0}, {'patientAddedDate': {$ne: null}}] }}",
            "{$project: {'patientAddedDate': {$dateFromString: '%m/%d/%y %H:%M:%S'}}}",
            "{$group: {_id: {$dateFromString: '%m/%d/%y'}, count: {$sum: 1}}}"})
    public List<PatientChartList> getChart(String user);
    */

    @Aggregation(pipeline = {"{$match: {'patientId': {$regex: //}}}",
                            "{$skip: ?1}",
                            "{$limit: ?0}",
                            "{$project: {'patientId': 1, 'image': 1, 'patientVillagename': 1," +
                                    "'patientMunicipality': 1, 'patientAddedDate': 1, 'patientPhone': 1," +
                                    "'patientFullName': 1}}"
                            })
    public List<PatientShortDetails> patientShortDetails(int perPage, int currentPage);

    @Aggregation(pipeline = {"{$match: {$and: [{'latitude': {$ne: null, $exists: true}}, {'longitude': {$ne: null, $exists: true}}]}}",
            "{$project: {'latitude': 1, 'longitude': 1, 'firstname': 1," +
                    "'lastname': 1}}"
    })
    public List<LocationPatient> patientLocationDetails();

   @Aggregation(pipeline = {"{$match: {'visit_category': ?0}}",
                            "{$group: {_id: 1, count: {$sum: 1}}}"})
   public Integer getCounts(String category);
}
