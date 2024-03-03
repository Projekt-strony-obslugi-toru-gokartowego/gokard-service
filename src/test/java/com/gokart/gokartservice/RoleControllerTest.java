package com.gokart.gokartservice;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.gokart.gokartservice.config.SecurityConfig;
import com.gokart.gokartservice.config.SecurityRoles;

// @Import(SecurityConfig.class)
// @ContextConfiguration(classes = { //
// RoleControllerTest.RoleController.class, //
// UserService.class //
// })
// @WebMvcTest(RoleControllerTest.RoleController.class)
// @EnableJpaRepositories


@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = RoleControllerTest.RoleController.class)
@Disabled
class RoleControllerTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @RestController
  public static class RoleController {

    @GetMapping("/api/client/test")
    @PreAuthorize("""
        hasRole(T(com.gokart.gokartservice.config.SecurityRoles).CLIENT
        )
        """)
    public String clientTest() {
      return "Accessible by CLIENT, WORKER, and ADMINISTRATOR";
    }

    @GetMapping("/api/worker/test")
    @PreAuthorize("""
        hasRole(T(com.gokart.gokartservice.config.SecurityRoles).WORKER)
        """)
    public String workerTest() {
      return "Accessible by WORKER and ADMINISTRATOR";
    }

    @GetMapping("/api/admin/test")
    @PreAuthorize("""
        hasRole(T(com.gokart.gokartservice.config.SecurityRoles).ADMINISTRATOR
        )
        """)
    public String adminTest() {
      return "Only accessible by ADMINISTRATOR";
    }
  }

  @ParameterizedTest
  @EnumSource(SecurityRoles.class)
  void clientEndpoint_AccessWithAllAvailableRoles_ReturnOk(SecurityRoles securityRole)
      throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/client/test")
            .with(user("user").roles(securityRole.name())))
        .andExpect(status().isOk())
        .andExpect(content().string("Accessible by CLIENT, WORKER, and ADMINISTRATOR"));
  }

  @ParameterizedTest
  @EnumSource(value = SecurityRoles.class, names = {"ADMINISTRATOR", "WORKER"})
  void workerEndpoint_AccessWithAdminAndWorkerRole_ReturnOk(SecurityRoles securityRole)
      throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/worker/test")
            .with(user("user").password("test").roles(securityRole.name())))
        .andExpect(status().isOk())
        .andExpect(content().string("Accessible by WORKER and ADMINISTRATOR"));
  }

  @ParameterizedTest
  @EnumSource(value = SecurityRoles.class, names = {"ADMINISTRATOR"})
  void administratorEndpoint_AccessWithAdminRole_ReturnOk(SecurityRoles securityRole)
      throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/admin/test")
            .with(user("user").roles(securityRole.name())))
        .andExpect(status().isOk()).andExpect(content().string("Only accessible by ADMINISTRATOR"));
  }

  @ParameterizedTest
  @EnumSource(value = SecurityRoles.class, names = {"CLIENT"})
  void workerEndpoint_AccessWithClientRole_ReturnForbidden(SecurityRoles securityRole)
      throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/api/worker/test")
        .with(user("user").roles(securityRole.name()))).andExpect(status().isForbidden());
  }

  @ParameterizedTest
  @EnumSource(value = SecurityRoles.class, names = {"CLIENT", "WORKER"})
  void administratorEndpoint_AccessWithWorkerAndClientRole_ReturnForbidden(
      SecurityRoles securityRole) throws Exception {

    mockMvc.perform(
        MockMvcRequestBuilders.get("/api/admin/test").with(user("user").roles(securityRole.name())))
        .andExpect(status().isForbidden());
  }
}
