package com.ayata.urldatabase.model.bridge.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinalResponse {
    private String code;
    private String status;
    private String message;
    private Object data;
    private Object details;

    public FinalResponse(String[] statusCode, String message){
        this.code = statusCode[0];
        this.status = statusCode[1];
        this.message = message;
    }

    public FinalResponse(String[] statusCode, String message, Object data, Object details){
        this.code = statusCode[0];
        this.status = statusCode[1];
        this.message = message;
        this.data = data;
        this.details = details;
    }
}
