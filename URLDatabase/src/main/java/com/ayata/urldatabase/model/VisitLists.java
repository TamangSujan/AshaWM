package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "visitlists")
public class VisitLists{
    @Id
    private String _id;
    private ArrayList<Visit> visit;
    private String user_id;
    private String patientId;
    private String __v;
}
