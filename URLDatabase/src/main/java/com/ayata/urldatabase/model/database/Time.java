package com.ayata.urldatabase.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Time{
    private Integer hour;
    private Integer minute;
    private Integer nano;
    private Integer second;
}
