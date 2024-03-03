package com.gokart.gokartservice.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({RuntimeException.class})
  public ErrorResponse handleRuntimeException(RuntimeException exception) {

    return ErrorResponse.builder(exception, HttpStatus.BAD_REQUEST, exception.getMessage()).build();
  }
}
