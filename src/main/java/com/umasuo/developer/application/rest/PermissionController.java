package com.umasuo.developer.application.rest;

import static com.umasuo.developer.infrastructure.Router.ACCEPTOR_PERMISSION_ROOT;
import static com.umasuo.developer.infrastructure.Router.APPLICANT_PERMISSION_ROOT;
import static com.umasuo.developer.infrastructure.Router.PERMISSION_WITH_ID;

import com.umasuo.developer.application.dto.ResourcePermissionView;
import com.umasuo.developer.application.service.ResourcePermissionApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Davis on 17/6/8.
 */
@CrossOrigin
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
   * @return the list
   */
  @GetMapping(APPLICANT_PERMISSION_ROOT)
  public List<ResourcePermissionView> getAllForApplicant(@RequestHeader String developerId) {
    LOG.info("Enter. applicantId: {}.", developerId);

    List<ResourcePermissionView> result = permissionApplication.findByApplicant(developerId);

    LOG.info("Exit. permission size: {}.", result.size());

    return result;
  }

  /**
   * Gets all permissions for acceptor.
   *
   * @param developerId the developer id
   * @return the all for acceptor
   */
  @GetMapping(ACCEPTOR_PERMISSION_ROOT)
  public List<ResourcePermissionView> getAllForAcceptor(@RequestHeader String developerId) {
    LOG.info("Enter. acceptorId: {}.", developerId);

    List<ResourcePermissionView> result = permissionApplication.findByAcceptor(developerId);

    LOG.info("Exit. permission size: {}.", result.size());

    return result;
  }

  /**
   * Delete permission by acceptor.
   *
   * @param developerId the acceptor id
   * @param id the Permission id
   */
  @DeleteMapping(PERMISSION_WITH_ID)
  public void delete(@RequestHeader("developerId") String developerId,
      @PathVariable("id") String id) {
    LOG.info("Enter. developerId: {}, permissionId: {}.", developerId, id);

    // TODO: 17/6/9 取消权限，需要把permission删除，并把对应的user权限也删除

    LOG.info("Exit.");
  }
}
