package com.umasuo.developer.application.rest;

import static com.umasuo.developer.infrastructure.Router.DEVELOPER_RESET_PASSWORD;

import com.umasuo.developer.application.dto.action.ResetPassword;
import com.umasuo.developer.application.service.DeveloperApplication;
import com.umasuo.developer.application.service.VerificationApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Davis on 17/7/6.
 */
public class ResetController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ResetController.class);

  @Autowired
  private transient VerificationApplication verificationApplication;

  @Autowired
  private transient DeveloperApplication developerApplication;

  @PostMapping(value = DEVELOPER_RESET_PASSWORD)
  public void getResetPasswordCode(@RequestParam String email) {
    LOG.info("Enter. email: {}.", email);

    verificationApplication.sendResetToken(email);

    LOG.info("Exit.");
  }

  @PutMapping(value = DEVELOPER_RESET_PASSWORD)
  public void resetPassword(@RequestBody ResetPassword resetRequest) {
    LOG.info("Enter.");
    developerApplication.resetPassword(resetRequest);
    LOG.info("Exit.");
  }
}
