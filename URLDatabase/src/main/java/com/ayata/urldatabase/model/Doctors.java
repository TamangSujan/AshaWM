package com.ayata.urldatabase.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Doctors{
    @Id
    public String _id;
    public Integer doc_id;
    public String phone;
    public String password;
    public String name;
    public String address;
    public String bio;
    public String __v;
}
