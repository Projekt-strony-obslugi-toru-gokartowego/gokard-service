package com.gokart.gokartservice.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
  @Column(name = "ID")
  private String id;

  @Column(name = "EMAIL", unique = true, nullable = false)
  private String email;

  @Column(name = "ENCRYPTED_PASSWORD")
  private String encryptedPassword;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastname;

  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;

  @Column(name = "PHONE_NUMBER_REGION")
  private String phoneNumberRegion;

  @Column(name = "DATE_OF_BIRTH")
  private LocalDate localDate;

  @Column(name = "ROLE")
  @Enumerated(EnumType.STRING)
  private SecurityRoles role = SecurityRoles.CLIENT;
}
