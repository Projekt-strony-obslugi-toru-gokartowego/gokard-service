package com.gokart.gokartservice.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.NumberParseException;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler implements AuthenticationEntryPoint {

  private static final String VALIDATION_ERROR = "Validation failed";
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final Tracer tracer;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    response.getOutputStream()
        .println(objectMapper.writeValueAsString(createCustomErrorResponse(HttpStatus.UNAUTHORIZED,
            request, authException.getMessage(), null).getBody()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomErrorResponse> handleValidationException(
      MethodArgumentNotValidException exception, HttpServletRequest request) {

    List<String> errorDetails = exception.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage()).toList();

    return createCustomErrorResponse(HttpStatus.BAD_REQUEST, request, VALIDATION_ERROR,
        errorDetails);
  }

  @ExceptionHandler(NumberParseException.class)
  public ResponseEntity<CustomErrorResponse> handleValidationException(
      NumberParseException exception, HttpServletRequest request) {

    return createCustomErrorResponse(HttpStatus.BAD_REQUEST, request, VALIDATION_ERROR,
        List.of(exception.getMessage()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<CustomErrorResponse> handleAccessDenied(AccessDeniedException exception,
      HttpServletRequest request) {

    return createCustomErrorResponse(HttpStatus.FORBIDDEN, request, exception.getMessage(),
        List.of(exception.getLocalizedMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomErrorResponse> handleAnyException(Exception exception,
      HttpServletRequest request) {

    return createCustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request,
        exception.getMessage(), List.of(exception.getLocalizedMessage()));
  }



  private ResponseEntity<CustomErrorResponse> createCustomErrorResponse(HttpStatus httpStatus,
      HttpServletRequest request, String message, List<String> details) {

    if (httpStatus.is5xxServerError()) {
      log.error(message);
    }

    String traceId = null;
    String spanId = null;
    Span currentSpan = tracer.currentSpan();
    if (currentSpan != null) {
      TraceContext context = currentSpan.context();
      traceId = context.traceId();
      spanId = context.spanId();
    }

    return new ResponseEntity<>( //
        CustomErrorResponse.builder(httpStatus, message, details)
            .timeStamp(LocalDateTime.now(ZoneOffset.UTC).toString()) //
            .path(request.getRequestURI()) //
            .traceId(traceId) //
            .spanId(spanId) //
            .build(),
        httpStatus);
  }
}
