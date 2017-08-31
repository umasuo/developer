package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.AuthStatus;
import com.umasuo.developer.application.service.StatusService;
import com.umasuo.developer.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Status controller.
 */
@RestController
@CrossOrigin
public class StatusController {
  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(StatusController.class);

  /**
   * status service.
   */
  @Autowired
  private transient StatusService statusService;

  /**
   * 检查开发者权限：是否已经登陆，是否拥有相应权限，token与ID是否对应等。
   * 内部接口，api-gateway不配置相关的路径。
   *
   * @param id    开发者ID
   * @param token token string
   * @return Auth Status
   */
  @GetMapping(value = Router.DEVELOPER_SIGN_IN_STATUS)
  public AuthStatus getAuthStatus(@PathVariable String id,
                                  @RequestParam @Valid @NotNull String token) {
    LOGGER.info("Enter. developerId: {}, tokenStr: {}.", id, token);

    AuthStatus status = statusService.checkAuthStatus(id, token);

    LOGGER.info("Exit. authStatus: {}", status);
    return status;
  }
}
