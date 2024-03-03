package com.gokart.gokartservice.user;

import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gokart.gokartservice.region.RegionService;
import com.gokart.gokartservice.user.api.v1.model.RegistrationRequest;
import com.gokart.gokartservice.user.api.v1.model.RoleChangeRequest;
import com.gokart.gokartservice.user.api.v1.model.UserResponse;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RegionService regionService;
  private final PasswordEncoder passwordEncoder;

  public void registerNewUser(RegistrationRequest registrationRequest) {

    regionService.validatePhoneNumber(registrationRequest.phoneNumber(),
        registrationRequest.phoneRegion());

    Optional<UserEntity> existingUser = userRepository.findByEmail(registrationRequest.email());
    if (existingUser.isPresent()) {
      throw new RuntimeException(String.format("User with the email address '%s' already exists.",
          registrationRequest.email()));
    }

    UserEntity userEntity = createUser(registrationRequest);
    userRepository.save(userEntity);
  }



  private UserEntity createUser(RegistrationRequest registrationRequest) {

    UserEntity entity = new UserEntity();
    entity.setEmail(registrationRequest.email());
    entity.setEncryptedPassword(passwordEncoder.encode(registrationRequest.password()));

    entity.setFirstName(registrationRequest.firstName());
    entity.setLastname(registrationRequest.lastname());
    entity.setLocalDate(registrationRequest.birthdate());
    entity.setPhoneNumber(registrationRequest.phoneNumber());
    entity
        .setPhoneNumberRegion(Objects.requireNonNullElse(registrationRequest.phoneRegion(), "PL"));

    return entity;
  }


  public Page<UserResponse> getUserList(Pageable pageable, String email, String firstname,
      String lastname) {

    // var specification = buildSpecificationForUserEntities(login, email, firstname, lastname);

    return userRepository.findAll(pageable)
        .map(entity -> new ModelMapper().map(entity, UserResponse.class));
  }

  @NotNull
  private static Specification<UserEntity> buildSpecificationForUserEntities(

      String login, String email, String firstname, String lastname) {

    // return (userEntity, cq, cb) -> {
    // cb.equal(cb.lower(UserEntity_.login))
    // }
    return null;
  }

  public void changeUserRole(RoleChangeRequest request) {

    userRepository.findByEmail(request.email()).ifPresent( //
        foundEntity -> foundEntity.setRole(request.securityRole()));
  }
}