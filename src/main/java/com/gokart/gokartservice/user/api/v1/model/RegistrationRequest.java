package com.gokart.gokartservice.user.api.v1.model;

import static com.gokart.gokartservice.common.Constants.LOCAL_DATE_FORMAT;

import java.io.InputStream;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationRequest( //
    @Schema(description = "email", example = "sample@gmail.com") //
    @Email(message = "Invalid email format") //
    @NotBlank(message = "Email cannot be blank") //
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

    @Schema(description = "Phone Region not required", example = "PL") //
    String phoneRegion, //

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_FORMAT) //
    @JsonSerialize(using = LocalDateTimeSerializer.class) //
    @NotNull //
    @NotBlank //
    LocalDate birthdate, //
    @Schema(description = "Link with you avatar") //
    String photo //
) {
}
