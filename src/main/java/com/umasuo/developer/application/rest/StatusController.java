package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.service.StatusService;
import com.umasuo.developer.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/3/7.
 */
@RestController
public class StatusController {
  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(StatusController.class);

  /**
   * status service.
   */
  @Autowired
  private transient StatusService statusService;

  /**
   * get sign in status.
   * used by other services.
   *
   * @param id
   * @return
   */
  @GetMapping(value = Router.DEVELOPER_SIGN_IN_STATUS)
  public boolean getLoginStatus(@RequestParam @Valid @NotNull String id) {
    logger.info("SignInStatus: id: {}", id);

    boolean status = statusService.checkSignInStatus(id);

    logger.info("SignInStatus: id: {}, status: {}", id, status);
    return status;

  }
}
