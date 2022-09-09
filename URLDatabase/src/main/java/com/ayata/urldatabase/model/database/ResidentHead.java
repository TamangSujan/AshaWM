package com.ayata.urldatabase.model.database;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentHead{
    private String resident_head_first_name;
    private String resident_head_last_name;
    private String resident_head_middle_name;
    private String resident_relation_with_househead;
}
