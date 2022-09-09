package com.ayata.urldatabase.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "infantvisits")
public class InfantVisits{
    @Id @JsonIgnore
    private String _id;
    @Field(value = "AppUserList")
    private ArrayList<AppUserList> appUserList;
    @JsonIgnore
    private boolean synced;
    @JsonIgnore
    private String user;
    @JsonIgnore
    private String __v;
}
