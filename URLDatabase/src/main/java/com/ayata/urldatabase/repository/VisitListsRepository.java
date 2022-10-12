package com.ayata.urldatabase.repository;

import com.ayata.urldatabase.model.bridge.TrimesterCount;
import com.ayata.urldatabase.model.database.VisitLists;
import com.ayata.urldatabase.model.database.Visits;
import com.ayata.urldatabase.routes.web.misc.Category;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VisitListsRepository extends MongoRepository<VisitLists, String> {

    @Aggregation(pipeline = "{$match: {'visit.visit_category': 'SAFE_MOTHERHOOD'}}")
    public List<VisitLists> getSafeMotherHood();

    @Aggregation(pipeline = "{$match: {'visit.visit_category': 'CHRONIC_DISEASE'}}")
    public List<VisitLists> getChronicCase();

    @Aggregation(pipeline = "{$match: {'user_id': ?0}}")
    public List<VisitLists> getVisitListByUserId(String id);

    @Aggregation(pipeline = {"{$match: {$and: [{'user_id': ?0},{'visit.visit_id' :{$nin : ?1}}]}}"})
    List<VisitLists> getVisitsExceptGivenList(String user, List<String> list);

    @Aggregation(pipeline = {"{$match: {$and: [{'user_id': ?0},{'visit.visit_id' :{$in : ?1}}]}}"})
    List<VisitLists> getVisitsOfGivenList(String user, List<String> list);

    @Aggregation(pipeline = {"{$match: {$and: [{'user_id': ?0},{'patientId' : ?1}]}}"})
    List<VisitLists> getVisitsList(String user, String patientId);

    @Aggregation(pipeline = "{$match: {'user_id': ?0}}")
    public VisitLists findByUserId(String user);

    @Aggregation(pipeline = {"{$match: {'visit.visit_category': ?0}}",
            "{$count: ?0}"})
    public Integer getCounts(String category);

    @Aggregation(pipeline = {"{$match: {'visit.visit_category': 'SAFE_MOTHERHOOD','user_id': ?0}}",
            "{$count: 'total'}}"})
    public Integer getPregnancyCount(String chwId);

    @Aggregation(pipeline = {"{$match: {'visit.visit_category': 'SAFE_MOTHERHOOD', 'user_id': ?0}}",
                            "{$unwind: '$visit'}",
                            "{$project: {'stringDate': {$toString: '$visit.modelVisitSafe.menstrualDate._menstrual_date_period_english'}}}",
                            "{$project: {'date': {$dateFromString: {'dateString': $stringDate, 'format': '%d/%m/%Y'}}}}",
                            "{$project: {'trimester': {$divide: [{$subtract: [new Date(), $date]}, ?1]}}}",
                            "{$project: {'roundoff': {$round: [$trimester, 0]}}}",
                            "{$project: {'trimester_phase': {"+
                                        "$switch: {"+
                                            "branches: ["+
                                                "{ case: { $lte: [ $roundoff, 13 ] }, then: 'trimester1' },"+
                                                "{ case: { $lte: [ $roundoff, 26 ] }, then: 'trimester2' }"+
                                                "{ case: { $lte: [ $roundoff, 40 ] }, then: 'trimester3' }"+
                                                "{ case: { $lte: [ $roundoff, 52] }, then: 'trimester4' }"+
                                            "],"+
                                            "default: 'finished'"+
                                            "}}"+
                                        "}},"})
    public List<String> getTrimesterCount(String chwId, Integer time);

    @Aggregation(pipeline = {"{$match: {$and: [{'visit.visit_category': ?0},{user_id: ?1}]}}",
                            "{$unwind: '$visit'}",
                            "{$project: {stringDate: {$toString: '$visit.visit_lastdate_english'}}}",
                            "{$project: {date: {$dateFromString:{dateString: '$stringDate', format: '%d/%m/%Y'}}}}",
                            "{$project: {remmonth: {$divide: [{$subtract: [new Date(), '$date']} , ?2]}}}",
                            "{$project: {newpreg: {$cond: {if: {$and: [{$gte: ['$remmonth', -2]}, {$lte: ['$remmonth', 0]}]}, then: 'true', else: 'false'}}}}",
                            "{$match: {newpreg: 'true'}}",
                            "{$count: 'count'}"})
    public Integer getNewChronicOrPregnancyCount(Category category, String chwId, Integer time);

    @Aggregation(pipeline = {"{$match: {$and: [{'visit.visit_category': 'SAFE_MOTHERHOOD'},{user_id: ?0}]}}",
            "{$unwind: '$visit'}",
            "{$project: {stringDate: {$toString: '$visit.modelVisitSafe.menstrualDate._menstrual_date_edd_english'}}}",
            "{$project: {date: {$dateFromString:{dateString: '$stringDate', format: '%d/%m/%Y'}}}}",
            "{$project: {remmonth: {$divide: [{$subtract: [new Date(), '$date']} , ?1]}}}",
            "{$project: {newdelivery: {$cond: {if: {$and: [{$gte: ['$remmonth', -2]}, {$lte: ['$remmonth', 0]}]}, then: 'true', else: 'false'}}}}",
            "{$match: {newdelivery: 'true'}}",
            "{$count: 'count'}"})
    public Integer getNewDeliveryCount(String chwId, Integer time);

    @Aggregation(pipeline = {"{$match: {$and: [{user_id: ?0}, {'visit.visit_category': 'SAFE_MOTHERHOOD'}, {'visit.modelVisitSafe.checkup._checkup_risk_sign': {$exists: true,$nin: ['NONE']}}]}}",
                            "{$unwind: $visit}",
                            "{$group: {_id: {patientId: '$patientId', visitId: '$visit.visit_id', ward: '$visit.ward', risk: '$visit.modelVisitSafe.checkup._checkup_risk_sign'}}}",
                            "{$count: 'total'}"})
    public Integer getRiskPregnancyCountByChwId(String chwId);

    @Aggregation(pipeline = {"{$match: {$and: [{user_id: ?0}, {'visit.visit_category': 'SAFE_MOTHERHOOD'}, {'visit.modelVisitSafe.termination._termination_complication': {$exists: true,$nin: ['NONE']}}]}}",
            "{$unwind: $visit}",
            "{$group: {_id: {patientId: '$patientId', visitId: '$visit.visit_id', ward: '$visit.ward', complication: '$visit.modelVisitSafe.termination._termination_complication'}}}",
            "{$count: 'total'}"})
    public Integer getComplicationDeliveryCountByChwId(String chwId);

    @Aggregation(pipeline = {"{$match: {'visit.visit_followupdate_english': {$exists: true, $ne: null}, user_id: ?0}}",
                            "{$unwind: '$visit'}",
                            "{$project: {stringDate: {$toString: '$visit.visit_followupdate_english'}, patientId: '$patientId'}}",
                            "{$project: {date: {$dateFromString: {dateString: '$stringDate', format: '%d/%m/%Y'}}, patientId: '$patientId'}}",
                            "{$match: {date: {$lt: new Date()}}}",
                            "{$group: {_id: '$patientId'}}",
                            "{$count: 'total'}"})
    public Integer getFollowUpdateCount(String chwId);


    @Aggregation(pipeline = {"{$match: {$and: [{user_id: ?0}, {'visit.visit_category': 'CHRONIC_DISEASE'}]}}",
            "{$unwind: $visit}",
            "{$match: {'visit.modelVisitChronic.cDisease.diseaseName': {$exists: true, $ne: null, $nin: ['NONE']}}}",
            "{$group: {_id: {patientId: '$patientId', chronic: '$visit.modelVisitChronic.cDisease.diseaseName'}}}",
            "{$count: 'total'}"})
    public Integer getChronicDiseaseCount(String chwId);

    @Aggregation(pipeline = {"{$match: {$and: [{user_id: ?0}, {'visit.visit_category': 'CHRONIC_DISEASE'}]}}",
            "{$unwind: $visit}",
            "{$match: {'visit.modelVisitChronic.cRisk.riskName': {$exists: true, $ne: null, $nin: ['NONE']}}}",
            "{$group: {_id: {patientId: '$patientId', chronic: '$visit.modelVisitChronic.cRisk.riskName'}}}",
            "{$count: 'total'}"})
    public Integer getRiskPatientCount(String chwId);

    @Aggregation(pipeline = {"{$match: {'visit.visit_category': 'SAFE_MOTHERHOOD'}}",
                            "{$sort: {_id: 1}}"})
    public List<VisitLists> getPregnantList();
    @Aggregation(pipeline = {"{$match: {'visit.visit_category': 'SAFE_MOTHERHOOD', user_id: ?0}}",
            "{$sort: {_id: 1}}"})
    public List<VisitLists> getPregnantListByChwId(String chwId);

    @Aggregation(pipeline = {"{$match: {'visit.visit_followupdate_english': {$exists: true, $ne: null}}}",
            "{$unwind: '$visit'}",
            "{$project: {stringDate: {$toString: '$visit.visit_followupdate_english'}, patientId: '$patientId'}}",
            "{$project: {date: {$dateFromString: {dateString: '$stringDate', format: '%d/%m/%Y'}}, patientId: '$patientId'}}",
            "{$match: {date: {$lt: new Date()}}}",
            "{$group: {_id: '$patientId'}}"})
    public List<Object> getFollowUpdate();

    @Aggregation(pipeline = {"{$match: {'visit.visit_category': 'CHRONIC_DISEASE'}}",
            "{$unwind: $visit}",
            "{$match: {'visit.modelVisitChronic.cDisease.diseaseName': {$exists: true, $ne: null, $nin: ['NONE']}}}"})
    public List<Object> getChronicPatients();

    @Aggregation(pipeline = {"{$match: {'visit.visit_category': 'CHRONIC_DISEASE', 'visit.modelVisitChronic.cRisk.riskName': {$exists: true, $ne: null, $nin: ['NONE']}}}",
            "{$unwind: $visit}",
            "{$match: {}}"})
    public List<Object> getRiskPatients();

}
