package com.gokart.gokartservice.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String email) {

    UserEntity user = repository.findByEmail(email).orElseThrow(
        () -> new UserNotFoundException(String.format("User does not exist, email: %s", email)));

    return org.springframework.security.core.userdetails.User.builder() //
        .username(user.getEmail()) //
        .password(user.getEncryptedPassword()) //
        .authorities(user.getRole().getRoleWithPrefix()) //
        .build();
  }
}
