package com.gokart.gokartservice.role;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gokart.gokartservice.config.CustomErrorResponse;
import com.gokart.gokartservice.config.SecurityRoles;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RoleController.ROLE_ENDPOINT, produces = APPLICATION_JSON_VALUE)
@ApiResponse(responseCode = "500",
    content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
class RoleController {

  static final String ROLE_ENDPOINT = "/api/v1/roles";

  @Operation(summary = "Return list of available roles")
  @ApiResponse(responseCode = "200",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoleName.class))))
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("""
      hasRole(T(com.gokart.gokartservice.config.SecurityRoles).ADMINISTRATOR)
      """)
  List<RoleName> getRoles() {
    return Arrays.stream(SecurityRoles.values()).map(Enum::name).map(RoleName::new).toList();
  }
}
