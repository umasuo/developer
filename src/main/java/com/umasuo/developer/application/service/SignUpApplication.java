package com.umasuo.developer.application.service;

import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Sign up service.
 */
@Service
public class SignUpApplication {

  /**
   * logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignUpApplication.class);

  /**
   * Verify application.
   */
  @Autowired
  private transient VerificationApplication verificationApplication;

  /**
   * Developer service.
   */
  @Autowired
  private transient DeveloperService developerService;

  /**
   * Sign up.
   *
   * @param email
   * @param password
   */
  public void signUp(String email, String password) {
    LOGGER.debug("email: {}", email);

    Developer developer = developerService.create(email, password);

    Assert.notNull(developer, "Something wrong when developer sign up");

    verificationApplication.sendVerificationEmail(developer.getId(), developer.getEmail());
  }
}
