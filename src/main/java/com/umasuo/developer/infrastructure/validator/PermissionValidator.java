package com.umasuo.developer.infrastructure.validator;

import com.umasuo.developer.application.dto.PermissionRequest;
import com.umasuo.developer.domain.model.ResourcePermission;
import com.umasuo.exception.AuthFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/11.
 */
public final class PermissionValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PermissionValidator.class);

  /**
   * Instantiates a new Permission validator.
   */
  private PermissionValidator() {
  }

  /**
   * Validate developer.
   *
   * @param request the request
   * @param permissions the permissions
   */
  public static void validateDeveloper(PermissionRequest request,
      List<ResourcePermission> permissions) {
    final AtomicReference<Boolean> reference = new AtomicReference<>();
    reference.set(false);

    Consumer<ResourcePermission> consumer = permission -> {
      if (permission.getDeviceDefinitionId().equals(request.getDeviceDefinitionId()) &&
          permission.getReferences().containsAll(request.getReferences())) {
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
