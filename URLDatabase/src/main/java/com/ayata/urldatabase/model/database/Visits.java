package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visits{
    @Id
    private String _id;
    @Field(value = "AppUserList")
    private ArrayList<AppUserList> appUserList;
    private String user;
    private boolean synced;
    private String __v;
}
