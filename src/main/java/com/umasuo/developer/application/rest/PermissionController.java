package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.PermissionRequest;
import com.umasuo.developer.application.dto.ResourcePermissionView;
import com.umasuo.developer.application.service.ResourcePermissionApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Davis on 17/6/8.
 */
@RestController
public class PermissionController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PermissionController.class);

  /**
   * The Resource permission application.
   */
  @Autowired
  private ResourcePermissionApplication permissionApplication;

  /**
   * Get list.
   *
   * @param developerId the developer id
   * @param acceptorId the acceptor id
   * @return the list
   */
  public List<ResourcePermissionView> get(@RequestHeader String developerId, String acceptorId) {
    LOG.info("Enter. applicantId: {}, acceptorId: {}.", developerId, acceptorId);

    List<ResourcePermissionView> result = permissionApplication
        .findPermission(developerId, acceptorId);

    LOG.info("Exit. permission size: {}.", result.size());

    return result;
  }

  /**
   * Request user permission.
   *
   * @param developerId the developer id
   * @param request the request
   */
  public void requestUserPermission(@RequestHeader String developerId, PermissionRequest request) {
    // 1. 查看developer是否有对应permission
    // 2. 请求转发到user service
  }
}
