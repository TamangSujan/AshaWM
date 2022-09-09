package com.ayata.urldatabase.model.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

public class Visits{
    @Id
    public String _id;
    @Field(value = "AppUserList")
    public ArrayList<AppUserList> appUserList;
    public String user;
    public boolean synced;
    public String __v;
}
