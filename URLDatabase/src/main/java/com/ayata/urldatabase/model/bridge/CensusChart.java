package com.ayata.urldatabase.model.bridge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CensusChart {
    private int total;
    private List<CensusChartList> chart;
    private List<String> x;
    private List<String> y;
}
