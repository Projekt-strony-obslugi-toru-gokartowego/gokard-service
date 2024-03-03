package com.gokart.gokartservice.user.api.v1.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest( //
    @NotNull //
    @NotBlank //
    String email, //

    @NotNull //
    @NotBlank //
    String password //
) {
}
