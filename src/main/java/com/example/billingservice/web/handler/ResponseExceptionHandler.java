package com.example.billingservice.web.handler;

import com.example.billingservice.api.ErrorDto;
import com.example.billingservice.exception.BillValueLessNilException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BillValueLessNilException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorDto handleDuplicateCreateException(BillValueLessNilException exception) {
        return ErrorDto.builder()
                .code(HttpStatus.BAD_GATEWAY.value())
                .message(exception.getMessage())
                .build();
    }
}
