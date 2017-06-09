package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.ResourcePermissionView;
import com.umasuo.developer.application.dto.mapper.ResourcePermissionMapper;
import com.umasuo.developer.domain.model.ResourcePermission;
import com.umasuo.developer.domain.service.ResourcePermissionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
