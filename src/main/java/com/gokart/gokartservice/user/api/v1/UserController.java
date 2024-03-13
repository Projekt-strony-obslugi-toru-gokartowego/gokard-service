package com.gokart.gokartservice.user.api.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gokart.gokartservice.config.CustomErrorResponse;
import com.gokart.gokartservice.user.UserService;
import com.gokart.gokartservice.user.api.v1.model.RegistrationRequest;
import com.gokart.gokartservice.user.api.v1.model.RoleChangeRequest;
import com.gokart.gokartservice.user.api.v1.model.UserDetailsResponse;
import com.gokart.gokartservice.user.api.v1.model.UserResponse;
import com.gokart.gokartservice.user.api.v1.model.UserResponseFilterRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UserController.USERS_ENDPOINT, produces = APPLICATION_JSON_VALUE)
@ApiResponse(responseCode = "500",
    content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
class UserController {
  static final String USERS_ENDPOINT = "/api/v1/users";
  private final UserService userService;

  @Operation(summary = "Register new user endpoint")
  @ApiResponse(responseCode = "201")
  @ApiResponse(responseCode = "400",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  void registerNewUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
    userService.registerNewUser(registrationRequest);
  }

  @Operation(summary = "Endpoint which allows change user role")
  @ApiResponse(responseCode = "204")
  @ApiResponse(responseCode = "400",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @ApiResponse(responseCode = "401",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("""
      hasRole(T(com.gokart.gokartservice.config.SecurityRoles).ADMINISTRATOR)
      """)
  void updateUserRole(@Valid @RequestBody RoleChangeRequest request) {
    userService.changeUserRole(request);
  }

  @Operation(summary = "Retrieve list of available users only for worker and above")
  @ApiResponse(responseCode = "200")
  @ApiResponse(responseCode = "400",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @ApiResponse(responseCode = "401",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("""
      hasRole(T(com.gokart.gokartservice.config.SecurityRoles).WORKER)
      """)
  Page<UserResponse> getUserList( //
      @RequestParam(required = false) Pageable pageable, //
      @RequestParam(required = false) UserResponseFilterRequest filterRequest) {

    return userService.getUserList(Objects.requireNonNullElse(pageable, Pageable.ofSize(20)));
  }

  @Operation(summary = "Details about user - for worker")
  @ApiResponse(responseCode = "200")
  @ApiResponse(responseCode = "400",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @ApiResponse(responseCode = "401",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @GetMapping("/{userEmail}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("""
      hasRole(T(com.gokart.gokartservice.config.SecurityRoles).WORKER)
      """)
  UserDetailsResponse getUserDetails(@PathVariable String userEmail) {

    return userService.getUserDetails(userEmail);
  }

  @Operation(summary = "Details about logged in user")
  @ApiResponse(responseCode = "200")
  @ApiResponse(responseCode = "400",
          content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @ApiResponse(responseCode = "401",
          content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @ApiResponse(responseCode = "403",
          content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
  @GetMapping("/details")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("""
      hasRole(T(com.gokart.gokartservice.config.SecurityRoles).CLIENT)
      """)
  UserDetailsResponse getUserDetailsClientEnd(HttpServletRequest request) {

    // Get the Principal object representing the user
    Principal userPrincipal = request.getUserPrincipal();
    return userService.getUserDetails(userPrincipal.getName());
  }

}
