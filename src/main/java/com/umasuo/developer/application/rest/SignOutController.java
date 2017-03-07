package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.service.SignOutService;
import com.umasuo.developer.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/3/7.
 */
@RestController
public class SignOutController {


  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignOutController.class);

  /**
   * sign in service.
   */
  @Autowired
  private transient SignOutService signOutService;

  /**
   * sign in.
   *
   * @param token
   * @return
   */
  @DeleteMapping(value = Router.DEVELOPER_SIGN_OUT)
  public void signIn(@RequestParam @Valid @NotNull String token) {
    logger.info("Token: {}", token);

    signOutService.signOut(token);

    return;
  }
}
