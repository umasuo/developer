package com.umasuo.developer.application.rest;

import static com.umasuo.developer.infrastructure.Router.DEVELOPER_VERIFY;
import static com.umasuo.developer.infrastructure.Router.DEVELOPER_WITH_ID;
import static com.umasuo.developer.infrastructure.Router.ID;

import com.umasuo.developer.application.service.VerificationApplication;
import com.umasuo.exception.AuthFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Davis on 17/7/6.
 */
@CrossOrigin
@RestController
public class VerifyController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(VerifyController.class);

  @Autowired
  private transient VerificationApplication verificationApplication;

  @GetMapping(value = DEVELOPER_VERIFY, params = "code")
  public void verifyEmail(@PathVariable(ID) String developerId,
      @RequestParam("code") String code) {
    LOG.info("Enter. developerId: {}, token: {}.", developerId, code);

    verificationApplication.verifyEmail(developerId, code);

    LOG.info("Exit.");
  }

  @PostMapping(value = DEVELOPER_VERIFY)
  public void sendVerifyEmail(@PathVariable(ID) String id,
      @RequestHeader("developerId") String developerId) {
    LOG.info("Enter. id: {}, developerId: {}.", id, developerId);

    // TODO: 17/7/4 最好移到一个统一的地方
    if (!id.equals(developerId)) {
      LOG.debug("Developer: {} Can not update other developer: {}.", developerId, id);
      throw new AuthFailedException("Developer do not have auth to update other developer");
    }

    verificationApplication.resendVerifyEmail(id);

    LOG.info("Exit.");
  }
}
