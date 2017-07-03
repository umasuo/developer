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
 * Created by umasuo on 17/3/7.
 */
@CrossOrigin
@RestController
public class SignInController {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignInController.class);

  /**
   * sign in service.
   */
  @Autowired
  private transient SignInApplication signInService;

  /**
   * sign in.
   *
   * @param signIn
   * @return
   */
  @PostMapping(value = Router.DEVELOPER_SIGN_IN)
  public SignInResult signIn(@RequestBody @Valid SignIn signIn) {
    logger.info("SignIn: {}", signIn);

    SignInResult result = signInService.signInWithEmail(signIn.getEmail(), signIn.getPassword());

    logger.info("SignInResult: {}", result);
    return result;
  }
}
