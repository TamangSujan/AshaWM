package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserList{
    private String appUserId;
    private ArrayList<ModelPatientList> modelPatientList;
}
