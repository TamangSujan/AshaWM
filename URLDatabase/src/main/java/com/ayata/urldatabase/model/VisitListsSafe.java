package com.ayata.urldatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(value = "visitlists")
@AllArgsConstructor
@NoArgsConstructor
public class VisitListsSafe {
    public String _id;
    public ArrayList<VisitSafe> visit;
    public String user_id;
    public String patientId;
    public String __v;
}
