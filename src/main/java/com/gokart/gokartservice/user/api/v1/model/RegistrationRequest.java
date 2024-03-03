package com.gokart.gokartservice.user.api.v1.model;

import static com.gokart.gokartservice.common.Constants.LOCAL_DATE_FORMAT;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationRequest( //
    @Pattern( //
        regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", //
        message = "Email have to match RFC 5322 Standard.") //
    String email, //
    @NotNull //
    @NotBlank //
    @Size(min = 6) //
    String password, //
    @NotNull //
    @NotBlank //
    String firstName, //

    @NotNull //
    @NotBlank //
    String lastname, //

    @NotNull //
    @NotBlank //
    String phoneNumber, //

    String phoneRegion, //

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_FORMAT) //
    @JsonSerialize(using = LocalDateTimeSerializer.class) //
    LocalDate birthdate //
) {
}
