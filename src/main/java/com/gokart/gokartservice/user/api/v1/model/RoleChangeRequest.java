package com.gokart.gokartservice.user.api.v1.model;

import com.gokart.gokartservice.common.ValueOfEnum;
import com.gokart.gokartservice.config.SecurityRoles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleChangeRequest( //
    @NotNull //
    @NotBlank //
    String email, //
    @NotNull //
    @NotBlank //
    @ValueOfEnum(enumClass = SecurityRoles.class) //
    String securityRole //

) {
}
