package com.mycompany.todoapi.exception.handler;

import com.mycompany.todoapi.dto.ExceptionDto;
import com.mycompany.todoapi.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.mycompany.todoapi.exception.ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {BusinessException.class})
    ResponseEntity<ExceptionDto> handleBusinessException(BusinessException ex) {
        log.info("handle {} code: {}, message: {}", ex.getClass().getSimpleName(), ex.getCode(), ex.getMessage());
        var view = new ExceptionDto(ex.getCode().name(), ex.getMessage());
        return new ResponseEntity<>(view, ex.getCode().getHttpStatus());
    }

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    ResponseEntity<ExceptionDto> handleNotValidException(MethodArgumentNotValidException ex) {
        log.info("handle {} code: {}, message: {}", ex.getClass().getSimpleName(), ex.getFieldError(), ex.getMessage());
        var view = new ExceptionDto(ILLEGAL_ARGUMENT_EXCEPTION.name(), ex.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(view, ILLEGAL_ARGUMENT_EXCEPTION.getHttpStatus());
    }
}
