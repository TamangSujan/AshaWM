package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfantVisits{
    private String _id;
    private ArrayList<AppUserList> appUserList;
    private boolean synced;
    private String user;
    private String __v;
}
