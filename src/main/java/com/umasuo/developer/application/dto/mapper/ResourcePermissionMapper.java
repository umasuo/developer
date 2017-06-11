package com.umasuo.developer.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.developer.application.dto.ResourcePermissionView;
import com.umasuo.developer.domain.model.ResourcePermission;
import com.umasuo.developer.domain.model.ResourceRequest;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/9.
 */
public final class ResourcePermissionMapper {

  /**
   * Instantiates a new Resource permission mapper.
   */
  private ResourcePermissionMapper() {
  }

  /**
   * Build resource permission.
   *
   * @param request the request
   * @return the resource permission
   */
  public static ResourcePermission build(ResourceRequest request) {
    ResourcePermission entity = new ResourcePermission();

    entity.setAcceptorId(request.getAcceptorId());
    entity.setApplicantId(request.getApplicantId());
    entity.setDeviceDefinitionId(request.getDeviceDefinitionId());
    entity.setReferences(request.getReferences());

    return entity;
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<ResourcePermissionView> toModel(List<ResourcePermission> entities) {
    List<ResourcePermissionView> models = Lists.newArrayList();

    Consumer<ResourcePermission> consumer = permission -> models.add(toModel(permission));
    entities.stream().forEach(consumer);

    return models;
  }

  /**
   * To model resource permission view.
   *
   * @param entity the entity
   * @return the resource permission view
   */
  public static ResourcePermissionView toModel(ResourcePermission entity) {
    ResourcePermissionView model = new ResourcePermissionView();

    model.setId(entity.getId());
    model.setAcceptorId(entity.getAcceptorId());
    model.setApplicantId(entity.getApplicantId());
    model.setDeviceId(entity.getDeviceDefinitionId());
    model.setReferences(entity.getReferences());

    return model;
  }
}
