package com.gokart.gokartservice.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import com.gokart.gokartservice.config.SecurityRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Setter
@Getter
@Entity(name = "users")
public class UserEntity {

  @Id
  @UuidGenerator
  @GeneratedValue
  private String id;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column
  private String encryptedPassword;

  @Column
  private String firstName;

  @Column
  private String lastname;

  @Column
  private String phoneNumber;

  @Column
  private String phoneNumberRegion;

  @Column
  private LocalDate localDate;

  @Column
  private SecurityRoles role = SecurityRoles.CLIENT;
}
