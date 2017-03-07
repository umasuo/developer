package com.umasuo.developer.application.service;

import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by umasuo on 17/3/7.
 */
@Service
public class SignUpService {

  /**
   * logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignUpService.class);

  @Autowired
  DeveloperService developerService;

  public String SignUpService(String email, String password) {
    LOGGER.debug("SignUp: email: {}", email);

    Developer developer = developerService.create(email, password);
    Assert.notNull(developer);

    return null;
  }

}
