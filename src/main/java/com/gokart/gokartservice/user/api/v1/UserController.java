package com.gokart.gokartservice.user.api.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gokart.gokartservice.user.UserService;
import com.gokart.gokartservice.user.api.v1.model.LoginRequest;
import com.gokart.gokartservice.user.api.v1.model.LoginResponse;
import com.gokart.gokartservice.user.api.v1.model.RegistrationRequest;
import com.gokart.gokartservice.user.api.v1.model.RoleChangeRequest;
import com.gokart.gokartservice.user.api.v1.model.UserResponse;
import com.gokart.gokartservice.user.api.v1.model.UserResponseFilterRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UserController.USERS_ENDPOINT, produces = APPLICATION_JSON_VALUE)
class UserController {
  static final String USERS_ENDPOINT = "/api/v1/users";
  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  void registerNewUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
    userService.registerNewUser(registrationRequest);
  }

//  @PostMapping(value = "/login")
//  @ResponseStatus(HttpStatus.OK)
//  LoginResponse login(@Valid @RequestBody LoginRequest request) {
////    authenticationManager
////        .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
//    // String token = JwtHelper.generateToken(request.email());
//    return new LoginResponse(request.email(), "someToken");
//  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("""
      hasRole(T(com.gokart.gokartservice.config.SecurityRoles).ADMINISTRATOR)
      """)
  void updateUserRole(@Valid @RequestBody RoleChangeRequest request) {
    userService.changeUserRole(request);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("""
      hasRole(T(com.gokart.gokartservice.config.SecurityRoles).WORKER)
      """)
  Page<UserResponse> getUserList(Pageable pageable, //
      @Valid UserResponseFilterRequest filterRequest) {

    return userService.getUserList(pageable, //
        filterRequest.getEmail(), //
        filterRequest.getFirstname(), //
        filterRequest.getLastname() //
    );
  }
}
