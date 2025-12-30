package com.sangam.iot.consumer.model;

import java.util.List;
import lombok.Data;

@Data
public class ConsumerErrorResponse {

    private int errorCode;
    private String message;
    private List<String> errors;

    public ConsumerErrorResponse() {
        super();
    }

    public ConsumerErrorResponse(int errorCode, String message, List<String> errors) {
        super();
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

    public ConsumerErrorResponse(int errorCode, String message, String error) {
        super();
        this.errorCode = errorCode;
        this.message = message;
        errors = List.of(error);
    }

}
