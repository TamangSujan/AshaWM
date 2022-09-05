package com.ayata.urldatabase.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Visits{
    @Id
    public String _id;
    public ArrayList<AppUserList> appUserList;
    public String user;
    public boolean synced;
    public String __v;
}
