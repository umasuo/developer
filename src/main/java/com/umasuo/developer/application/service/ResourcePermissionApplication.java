package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.PermissionRequest;
import com.umasuo.developer.application.dto.ResourcePermissionView;
import com.umasuo.developer.application.dto.UserPermissionRequest;
import com.umasuo.developer.application.dto.mapper.ResourcePermissionMapper;
import com.umasuo.developer.application.dto.mapper.UserPermissionRequestMapper;
import com.umasuo.developer.domain.model.ResourcePermission;
import com.umasuo.developer.domain.service.ResourcePermissionService;
import com.umasuo.developer.infrastructure.validator.PermissionValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/6/9.
 * todo 暂时没用
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
   * The Rest client.
   */
  @Autowired
  private RestClient restClient;

  /**
   * Find permission.
   *
   * @param applicantId the applicant id
   * @return the list
   */
  public List<ResourcePermissionView> findByApplicant(String applicantId) {
    LOG.debug("Enter. applicantId: {}.", applicantId);

    List<ResourcePermission> permissions = permissionService.findByApplicant(applicantId);
    List<ResourcePermissionView> result = ResourcePermissionMapper.toModel(permissions);

    LOG.debug("Exit. permission size: {}.", result.size());

    return result;
  }

  /**
   * Find permission by acceptor.
   *
   * @param acceptorId the acceptor id
   * @return the list
   */
  public List<ResourcePermissionView> findByAcceptor(String acceptorId) {
    LOG.debug("Enter. acceptorId: {}.", acceptorId);

    List<ResourcePermission> permissions = permissionService.findByAcceptor(acceptorId);
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
        .findPermissions(request.getApplicantId(), request.getAcceptorId());
    PermissionValidator.validateDeveloper(request, permissions);
    // 2. 请求转发到user service
    UserPermissionRequest permissionRequest = UserPermissionRequestMapper.build(userId, request);
    restClient.forwardUserRequest(permissionRequest);

    LOG.info("Exit.");
  }
}
