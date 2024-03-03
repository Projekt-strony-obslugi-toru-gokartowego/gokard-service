package com.gokart.gokartservice.user.api.v1.model;

import com.gokart.gokartservice.config.SecurityRoles;

public record RoleChangeRequest( //
    String email, //
    SecurityRoles securityRole //

) {
}
