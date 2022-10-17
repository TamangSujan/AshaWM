package com.ayata.urldatabase.routes.web;

import com.ayata.urldatabase.model.bridge.Response.DashboardResponse;
import com.ayata.urldatabase.model.bridge.Response.FinalResponse;
import com.ayata.urldatabase.model.bridge.TrimesterCount;
import com.ayata.urldatabase.services.InfantService;
import com.ayata.urldatabase.services.PatientService;
import com.ayata.urldatabase.services.VisitService;
import com.ayata.urldatabase.static_files.StatusCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/web")
public class URLDashboard {

    private PatientService patientService;
    private VisitService visitService;
    private InfantService infantService;

    @GetMapping("/patient/stat/{chwId}")
    public ResponseEntity<?> getPatientStat(@PathVariable(value = "chwId")String chwId){
        int pregnantWomenCount = visitService.getPregnantCountByChwId(chwId);
        //TODO: Use Trimester using Spring WebFlux
        TrimesterCount trimesterCount = visitService.getTrimesterCount(chwId);
        int newPregnantWomenCount = visitService.getPregnantCountByChwId(chwId);
        int newChronicCount = visitService.getChronicDiseaseCount(chwId);
        int newDeliveryCount = visitService.getNewDeliveryCountByChwId(chwId);
        int riskPregnancyCount = visitService.getRiskPregnancyCount(chwId);
        int riskPatientCount = visitService.getRiskPatientCount(chwId);
        int complicatedDeliveryCount = visitService.getComplicateDeliveryCount(chwId);
        int notVisitedCount = visitService.getFollowUpdateCount(chwId);
        int chronicDiseaseCount = visitService.getChronicDiseaseCount(chwId);
        int infantCount = infantService.getInfantCount(chwId);

        DashboardResponse dashboardResponse = new DashboardResponse(pregnantWomenCount, trimesterCount.getTrimester1(),
        trimesterCount.getTrimester2(), trimesterCount.getTrimester3(), trimesterCount.getTrimester4(), trimesterCount.getTrimesterfinished(),
        newPregnantWomenCount, newChronicCount, newDeliveryCount, riskPregnancyCount, complicatedDeliveryCount, notVisitedCount,
        chronicDiseaseCount, riskPatientCount, infantCount);
        return ResponseEntity.status(HttpStatus.OK).body(new FinalResponse(StatusCode.OK, "Patient status successfully fetched", dashboardResponse, null));
    }
}
