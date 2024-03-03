package com.gokart.gokartservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class GokartserviceApplicationTest {

  @Test
  void runSpringBootApplication_ContextShouldBeSuccessfullyLaunched(ApplicationContext context) {

    assertNotNull(context, "Application context should be loaded");
  }
}
