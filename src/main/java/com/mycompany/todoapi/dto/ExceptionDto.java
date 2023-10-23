package com.mycompany.todoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDto {

    private final String errorCode;
    private final String message;
}