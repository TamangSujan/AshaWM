package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "infantvisitlists")
public class InfantVisitLists{
    @Id
    private String _id;
    private ArrayList<InfantVisit> infantVisit;
    private String user_id;
    private String infantId;
    private String __v;
}
