package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelVisitChronic{
    private CDiet cDiet;
    private CDisease cDisease;
    private CHabit cHabit;
    private CHealthFacility cHealthFacility;
    private CLab cLab;
    private CRisk cRisk;
}
