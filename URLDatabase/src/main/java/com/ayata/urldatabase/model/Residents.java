package com.ayata.urldatabase.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonRootName("cencusList")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Residents{
    @Id
    private String _id;
    private String user;
    private String residentId;
    private String residentAssignId;
    private Resident resident;
    private ResidentAddress residentAddress;
    private ResidentDetail residentDetail;
    private ResidentDetailFinal residentDetailFinal;
    private ResidentHead residentHead;
    private EnteredDateTime enteredDateTime;
}
