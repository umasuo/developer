package com.umasuo.developer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Application root.
 */
@SpringBootApplication(scanBasePackages = "com.umasuo")
@EnableAutoConfiguration
@Configuration
@RestController
@EnableAsync
public class Application {

  /**
   * Service name.
   */
  @Value("${spring.application.name}")
  private String serviceName;

  /**
   * This api is used for health check.
   *
   * @return service name.
   */
  @GetMapping("/")
  public String index() {
    return serviceName + ", system time: " + System.currentTimeMillis();
  }

  /**
   * Main root for application.
   *
   * @param args the args
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
