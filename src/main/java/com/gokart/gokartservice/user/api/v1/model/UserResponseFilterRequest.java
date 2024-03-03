package com.gokart.gokartservice.user.api.v1.model;

import lombok.Data;

@Data
public class UserResponseFilterRequest {

  private String email;
  private String firstname;
  private String lastname;
}
