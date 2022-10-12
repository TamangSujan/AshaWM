package com.ayata.urldatabase.model.bridge.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinalResponse {
    private String code;
    private String status;
    private String message;
    private Object data;
    private Object details;

    public FinalResponse(String code, String status){
        this.code = code;
        this.status = status;
    }

    public void setStatusCode(String code, String status){
        this.code = code;
        this.status = status;
    }
}
