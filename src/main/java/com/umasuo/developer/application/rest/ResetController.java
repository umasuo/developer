package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.action.ResetPassword;
import com.umasuo.developer.application.service.DeveloperApplication;
import com.umasuo.developer.application.service.VerificationApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.umasuo.developer.infrastructure.Router.DEVELOPER_RESET_PASSWORD;

/**
 * Reset password controller.
 */
@CrossOrigin
@RestController
public class ResetController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ResetController.class);

  /**
   * Verify application.
   */
  @Autowired
  private transient VerificationApplication verificationApplication;

  /**
   * Developer application.
   */
  @Autowired
  private transient DeveloperApplication developerApplication;

  /**
   * Get reset password code.
   *
   * @param email
   */
  @PostMapping(value = DEVELOPER_RESET_PASSWORD)
  public void getResetPasswordCode(@RequestParam String email) {
    LOGGER.info("Enter. email: {}.", email);

    verificationApplication.sendResetToken(email);

    LOGGER.info("Exit.");
  }

  /**
   * Reset password.
   *
   * @param resetRequest
   */
  @PutMapping(value = DEVELOPER_RESET_PASSWORD)
  public void resetPassword(@RequestBody ResetPassword resetRequest) {
    LOGGER.info("Enter.");

    developerApplication.resetPassword(resetRequest);

    LOGGER.info("Exit.");
  }
}
