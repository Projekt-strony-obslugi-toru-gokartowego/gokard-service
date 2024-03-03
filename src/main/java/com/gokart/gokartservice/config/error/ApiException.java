package com.gokart.gokartservice.config.error;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

  private final HttpStatus httpStatus;
  private List<String> details = Collections.emptyList();

  public ApiException(String message, Throwable cause, HttpStatus httpStatus,
      List<String> details) {
    super(message, cause);
    this.httpStatus = httpStatus;
    this.details = details;
  }

  public ApiException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
