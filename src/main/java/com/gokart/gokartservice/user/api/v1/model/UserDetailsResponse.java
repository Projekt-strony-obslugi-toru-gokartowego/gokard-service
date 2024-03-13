package com.gokart.gokartservice.user.api.v1.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDetailsResponse {
  private String firstName;
  private String lastname;
  private String email;
  private String phoneNumber;
  private String phoneNumberRegion;
  private LocalDate localDate;
  private String photoUrl; //
}
