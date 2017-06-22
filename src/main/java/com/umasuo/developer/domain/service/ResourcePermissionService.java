package com.umasuo.developer.domain.service;

import com.umasuo.developer.domain.model.ResourcePermission;
import com.umasuo.developer.infrastructure.repository.ResourcePermissionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/6/9.
 */
@Service
public class ResourcePermissionService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ResourcePermissionService.class);

  /**
   * The Repository.
   */
  @Autowired
  private ResourcePermissionRepository repository;

  /**
   * Save resource permission.
   *
   * @param permission the permission
   * @return the resource permission
   */
  public ResourcePermission save(ResourcePermission permission) {
    LOG.debug("Enter. permission: {}.", permission);

    ResourcePermission result = repository.save(permission);

    LOG.debug("Exit. permissionId: {}.", result.getId());

    return result;
  }

  /**
   * Find permission.
   *
   * @param applicantId the applicant id
   * @param acceptorId the acceptor id
   * @return the list
   */
  public List<ResourcePermission> findPermissions(String applicantId, String acceptorId) {
    LOG.debug("Enter. applicantId: {}, acceptorId: {}.", applicantId, acceptorId);
    ResourcePermission sample = new ResourcePermission();
    sample.setApplicantId(applicantId);
    sample.setAcceptorId(acceptorId);

    Example<ResourcePermission> example = Example.of(sample);

    List<ResourcePermission> permissions = repository.findAll(example);

    LOG.debug("Exit. permission size: {}.", permissions.size());
    return permissions;
  }

  /**
   * Find permission.
   *
   * @param applicantId the applicant id
   * @return the list
   */
  public List<ResourcePermission> findByApplicant(String applicantId) {
    LOG.debug("Enter. applicantId: {}.", applicantId);
    ResourcePermission sample = new ResourcePermission();
    sample.setApplicantId(applicantId);

    Example<ResourcePermission> example = Example.of(sample);

    List<ResourcePermission> permissions = repository.findAll(example);

    LOG.debug("Exit. permission size: {}.", permissions.size());
    return permissions;
  }


  /**
   * Find by acceptor.
   *
   * @param acceptorId the acceptor id
   * @return the list
   */
  public List<ResourcePermission> findByAcceptor(String acceptorId) {
    LOG.debug("Enter. acceptorId: {}.", acceptorId);
    ResourcePermission sample = new ResourcePermission();
    sample.setAcceptorId(acceptorId);

    Example<ResourcePermission> example = Example.of(sample);

    List<ResourcePermission> permissions = repository.findAll(example);

    LOG.debug("Exit. permission size: {}.", permissions.size());
    return permissions;
  }
}
