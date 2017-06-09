package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.PermissionRequest;
import com.umasuo.developer.application.dto.ResourcePermissionView;
import com.umasuo.developer.application.dto.mapper.ResourcePermissionMapper;
import com.umasuo.developer.domain.model.ResourcePermission;
import com.umasuo.developer.domain.service.ResourcePermissionService;
import com.umasuo.exception.AuthFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/9.
 */
@Service
public class ResourcePermissionApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ResourcePermissionApplication.class);

  /**
   * The Permission service.
   */
  @Autowired
  private ResourcePermissionService permissionService;

  /**
   * Find permission.
   *
   * @param applicantId the applicant id
   * @param acceptorId the acceptor id
   * @return the list
   */
  public List<ResourcePermissionView> findPermission(String applicantId, String acceptorId) {
    LOG.debug("Enter. applicantId: {}, acceptorId: {}.", applicantId, acceptorId);

    List<ResourcePermission> permissions =
        permissionService.findPermission(applicantId, acceptorId);
    List<ResourcePermissionView> result = ResourcePermissionMapper.toModel(permissions);

    LOG.debug("Exit. permission size: {}.", result.size());

    return result;
  }

  /**
   * Handle user permission request.
   *
   * @param userId the user id
   * @param request the request
   */
  public void handleUserPermissionRequest(String userId, PermissionRequest request) {
    LOG.info("Enter. userId: {}, request: {}.", userId, request);

    // 1. 查看developer是否有对应permission
    List<ResourcePermission> permissions = permissionService
        .findPermission(request.getApplicantId(), request.getAcceptorId());
    validateDeveloperPermission(request, permissions);
    // TODO: 17/6/9 转发
    // 2. 请求转发到user service
    
    LOG.info("Exit.");
  }

  private void validateDeveloperPermission(PermissionRequest request,
      List<ResourcePermission> permissions) {
    final AtomicReference<Boolean> reference = new AtomicReference<>();
    reference.set(false);

    Consumer<ResourcePermission> consumer = permission -> {
      if (permission.getDeviceId().equals(request.getDeviceDefinitionId()) &&
          permission.getReferences().containsAll(request.getReferences())){
        reference.set(true);
      }
    };
    permissions.stream().forEach(consumer);

    if (reference.get() == false) {
      LOG.debug("Developer: {} do not have permission to visit device: {} or resource: {}.",
          request.getApplicantId(), request.getDeviceDefinitionId(), request.getReferences());
      throw new AuthFailedException("Developer do not have auth to visit device or resource");
    }
  }
}
