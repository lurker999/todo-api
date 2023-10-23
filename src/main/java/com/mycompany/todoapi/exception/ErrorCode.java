package com.mycompany.todoapi.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    ILLEGAL_ARGUMENT_EXCEPTION(HttpStatus.PRECONDITION_FAILED),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND);
    private HttpStatus httpStatus;

    ErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
