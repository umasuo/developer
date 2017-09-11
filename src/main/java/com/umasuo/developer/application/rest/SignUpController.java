package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.SignIn;
import com.umasuo.developer.application.service.SignUpApplication;
import com.umasuo.developer.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Sign up controller.
 */
@CrossOrigin
@RestController
public class SignUpController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);

  /**
   * sign in application.
   */
  @Autowired
  private transient SignUpApplication signUpApplication;

  /**
   * sign up with email and password.
   */
  @PostMapping(value = Router.DEVELOPER_SIGN_UP)
  public void signUp(@RequestBody @Valid SignIn signUp) {
    LOGGER.info("Developer email: {}", signUp.getEmail());

    signUpApplication.signUp(signUp.getEmail(), signUp.getPassword());
  }
}
