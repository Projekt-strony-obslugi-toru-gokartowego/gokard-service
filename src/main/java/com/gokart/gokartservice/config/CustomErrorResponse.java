package com.gokart.gokartservice.config;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder(toBuilder = true, builderMethodName = "hiddenBuilder")
public class CustomErrorResponse {
  private final int statusCode;
  private final String status;
  private final String message;
  private final List<String> details;
  private String timeStamp;
  private String path;
  private String traceId;
  private String spanId;

  public static CustomErrorResponseBuilder builder(HttpStatus httpStatus, String message,
      List<String> details) {
    return hiddenBuilder().statusCode(httpStatus.value()).status(httpStatus.getReasonPhrase())
        .message(message).details(details);
  }
}
