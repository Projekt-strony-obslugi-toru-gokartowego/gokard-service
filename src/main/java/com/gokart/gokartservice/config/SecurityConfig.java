package com.gokart.gokartservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  static RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    String hierarchy = """
        ROLE_ADMINISTRATOR > ROLE_WORKER > ROLE_CLIENT
        """;
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
  }

  @Bean
  static MethodSecurityExpressionHandler methodSecurityExpressionHandler(
      RoleHierarchy roleHierarchy) {
    DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy);
    return handler;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http //
        .cors(AbstractHttpConfigurer::disable) //
        .csrf(AbstractHttpConfigurer::disable) //
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //
        .authorizeHttpRequests(auth -> auth
            // our public endpoints
            .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/users/login/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/regions").permitAll()
            .requestMatchers(HttpMethod.GET, "/authentication-docs/**").permitAll()
            // our private endpoints
            .anyRequest().authenticated())
        // Add JWT token filter
        .httpBasic(Customizer.withDefaults()).build();
  }
}
