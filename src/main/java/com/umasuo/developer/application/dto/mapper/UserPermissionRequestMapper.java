package com.umasuo.developer.application.dto.mapper;

import com.umasuo.developer.application.dto.PermissionRequest;
import com.umasuo.developer.application.dto.UserPermissionRequest;

/**
 * Created by Davis on 17/6/11.
 */
public final class UserPermissionRequestMapper {

  /**
   * Instantiates a new User permission request mapper.
   */
  private UserPermissionRequestMapper() {
  }

  /**
   * Build UserPermissionRequest.
   *
   * @param userId the user id
   * @param request the request
   * @return the user permission request
   */
  public static UserPermissionRequest build(String userId, PermissionRequest request) {
    UserPermissionRequest permissionRequest = new UserPermissionRequest();

    permissionRequest.setApplicantId(request.getApplicantId());
    permissionRequest.setUserId(userId);
    permissionRequest.setAcceptorId(request.getAcceptorId());
    permissionRequest.setDeviceDefinitionId(request.getDeviceDefinitionId());
    permissionRequest.setReferences(request.getReferences());

    return permissionRequest;
  }
}
