package com.gokart.gokartservice.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.gokart.gokartservice.user.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserService userService;
  private final GlobalExceptionHandler globalExceptionHandler;

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http //
        .cors(AbstractHttpConfigurer::disable) //
        .csrf(AbstractHttpConfigurer::disable) //
        .authorizeHttpRequests(auth -> auth
            // our public endpoints
            .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/users/login/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/regions").permitAll()
            .requestMatchers(HttpMethod.GET, "/authentication-docs/**").permitAll()
            // our private endpoints
            .anyRequest().authenticated())
        .httpBasic(withDefaults()) //
        .logout(
            logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll())
        .authenticationManager(authenticationManager(http)) //
            .exceptionHandling(exceptionConfig -> exceptionConfig.authenticationEntryPoint(globalExceptionHandler))
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
    return authenticationManagerBuilder.build();
  }
}
