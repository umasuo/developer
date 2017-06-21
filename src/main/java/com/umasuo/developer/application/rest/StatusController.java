package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.AuthStatus;
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
   * 检查开发者权限：是否已经登陆，是否拥有相应权限，token与ID是否对应等
   *
   * @param developerId    开发者ID
   * @param token token string
   * @return Auth Status
   */
  @GetMapping(value = Router.DEVELOPER_SIGN_IN_STATUS)
  public AuthStatus getAuthStatus(@RequestParam @Valid @NotNull String developerId, @RequestParam @Valid
  @NotNull String token) {
    logger.info("Enter. developerId: {}, tokenStr: {}.", developerId,token);

    AuthStatus status = statusService.checkAuthStatus(developerId, token);

    logger.info("Exit: authStatus: {}", status);
    return status;
  }
}
