package com.ayata.urldatabase.model.bridge;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrimesterCount {
    private Integer trimester1;
    private Integer trimester2;
    private Integer trimester3;
    private Integer trimester4;
    private Integer trimesterfinished;
}
