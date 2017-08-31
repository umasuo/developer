package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.service.SignOutApplication;
import com.umasuo.developer.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.umasuo.developer.infrastructure.Router.ID;

/**
 * Sign out
 */
@CrossOrigin
@RestController
public class SignOutController {


  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignOutController.class);

  /**
   * sign in service.
   */
  @Autowired
  private transient SignOutApplication signOutService;

  /**
   * sign in.
   *
   * @param token
   * @return
   */
  @DeleteMapping(value = Router.DEVELOPER_SIGN_OUT)
  public void signIn(@PathVariable(ID) String id, @RequestHeader @Valid String token) {
    LOGGER.info("Token: {}", token);

    signOutService.signOut(id, token);

  }
}
