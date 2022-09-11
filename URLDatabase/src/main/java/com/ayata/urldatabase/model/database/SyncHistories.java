package com.ayata.urldatabase.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "synchistories")
public class SyncHistories{
    @Id @JsonIgnore
    private String _id;
    @JsonIgnore
    private String id;
    @Field(value = "user")
    private String app_user_id;
    private String time;
    private String date;
    @JsonIgnore
    private String __v;
}
