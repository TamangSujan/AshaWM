package com.ayata.urldatabase.model.bridge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientChart {
    private Integer total;
    private List<PatientChartList> chart;
    private List<String> x;
    private List<Integer> y;
}
