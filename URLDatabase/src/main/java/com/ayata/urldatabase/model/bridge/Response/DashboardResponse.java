package com.ayata.urldatabase.model.bridge.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private int pregnantWomenCount;
    private int trimester1;
    private int trimester2;
    private int trimester3;
    private int trimester4;
    private int trimesterfinished;
    private int newPregnancyCount;
    private int newNcdCount;
    private int newDeliveryCount;
    private int riskPregnancyCount;
    private int complicationDeliveryCount;
    private int notVisitedCount;
    private int chronicPatientCount;
    private int riskPatientCount;
    private int infantCount;
}
