package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.PermissionRequest;
import com.umasuo.developer.application.service.ResourcePermissionApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static com.umasuo.developer.infrastructure.Router.USER_REQUEST_ROOT;

/**
 * Created by Davis on 17/6/22.
 * todo 暂时没用
 */
@CrossOrigin
@RestController
public class UserRequestController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UserRequestController.class);

  /**
   * The Resource permission application.
   */
  @Autowired
  private ResourcePermissionApplication permissionApplication;

  /**
   * Request user permission.
   *
   * @param userId  the user id
   * @param request the request
   */
  @PostMapping(USER_REQUEST_ROOT)
  public void requestUserPermission(@RequestHeader("userId") String userId,
                                    @RequestBody PermissionRequest request) {
    LOG.info("Enter. userId: {}, request: {}.", userId, request);

    permissionApplication.handleUserPermissionRequest(userId, request);

    LOG.info("Exit.");
  }
}
