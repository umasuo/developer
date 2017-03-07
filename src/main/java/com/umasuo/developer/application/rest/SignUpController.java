package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.SignIn;
import com.umasuo.developer.application.service.SignUpService;
import com.umasuo.developer.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by umasuo on 17/3/7.
 */
@RestController
public class SignUpController {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignInController.class);

  /**
   * sign in service.
   */
  @Autowired
  private transient SignUpService signUpService;

  /**
   * sign up with email and password.
   *
   * @param signUp
   */
  @PostMapping(value = Router.DEVELOPER_SIGN_UP)
  public void signUp(@RequestBody @Valid SignIn signUp) {
    logger.info("SignUp: {}", signUp);

    signUpService.signUp(signUp.getEmail(), signUp.getPassword());

    return;
  }

}
