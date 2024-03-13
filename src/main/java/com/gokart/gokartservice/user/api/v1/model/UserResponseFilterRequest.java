package com.gokart.gokartservice.user.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseFilterRequest {

  private String email;
  private String firstname;
  private String lastname;
}
