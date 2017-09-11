package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.SignIn;
import com.umasuo.developer.application.dto.SignInResult;
import com.umasuo.developer.application.service.SignInApplication;
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
 * Sign in controller.
 */
@CrossOrigin
@RestController
public class SignInController {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignInController.class);

  /**
   * sign in application.
   */
  @Autowired
  private transient SignInApplication signInApplication;

  /**
   * sign in.
   */
  @PostMapping(value = Router.DEVELOPER_SIGN_IN)
  public SignInResult signIn(@RequestBody @Valid SignIn signIn) {
    LOGGER.info("SignIn: {}", signIn);

    SignInResult result =
        signInApplication.signInWithEmail(signIn.getEmail(), signIn.getPassword());

    LOGGER.info("SignInResult: {}", result);
    return result;
  }
}
