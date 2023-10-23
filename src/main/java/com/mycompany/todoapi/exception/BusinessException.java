package com.mycompany.todoapi.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private final ErrorCode code;

    private final String message;
    public BusinessException(ErrorCode code, String message){
        super();
        this.message = message;
        this.code = code;
    }
}
