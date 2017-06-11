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
  public List<ResourcePermissionView> getAllForApplicant(@RequestHeader String developerId,
      String acceptorId) {
    LOG.info("Enter. applicantId: {}, acceptorId: {}.", developerId, acceptorId);

    List<ResourcePermissionView> result =
        permissionApplication.findForApplicant(developerId, acceptorId);

    LOG.info("Exit. permission size: {}.", result.size());

    return result;
  }

  /**
   * Gets all permissions for acceptor.
   *
   * @param developerId the developer id
   * @return the all for acceptor
   */
  public List<ResourcePermissionView> getAllForAcceptor(@RequestHeader String developerId) {
    LOG.info("Enter. acceptorId: {}.", developerId);

    List<ResourcePermissionView> result = permissionApplication.findByAcceptor(developerId);

    LOG.info("Exit. permission size: {}.", result.size());

    return result;
  }

  /**
   * Request user permission.
   *
   * @param userId the user id
   * @param request the request
   */
  public void requestUserPermission(@RequestHeader String userId, PermissionRequest request) {
    LOG.info("Enter. userId: {}, request: {}.", userId, request);

    permissionApplication.handleUserPermissionRequest(userId, request);

    LOG.info("Exit.");
  }

  public void delete() {
    // TODO: 17/6/9 取消权限，需要把permission删除，并把对应的user权限也删除
  }
}
