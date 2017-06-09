package com.umasuo.developer.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.developer.application.dto.ResourceRequestView;
import com.umasuo.developer.application.dto.ResourceRequestDraft;
import com.umasuo.developer.domain.model.ResourceRequest;
import com.umasuo.developer.infrastructure.enums.ReplyRequest;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/9.
 */
public final class ResourceRequestMapper {

  /**
   * Instantiates a new Resource request mapper.
   */
  private ResourceRequestMapper() {
  }

  /**
   * To entity resource request.
   *
   * @param applicantId the applicant id
   * @param model the model
   * @return the resource request
   */
  public static ResourceRequest toEntity(String applicantId, ResourceRequestDraft model) {
    ResourceRequest entity = new ResourceRequest();

    entity.setApplicantViewed(false);
    entity.setAcceptorId(model.getAcceptorId());
    entity.setDeviceId(model.getDeviceDefinitionId());
    entity.setReferences(model.getReferences());
    entity.setReplyRequest(ReplyRequest.UNVIEW);
    entity.setApplicantId(applicantId);

    return entity;
  }

  /**
   * To model resource request view.
   *
   * @param entity the entity
   * @return the resource request view
   */
  public static ResourceRequestView toModel(ResourceRequest entity) {
    ResourceRequestView model = new ResourceRequestView();

    model.setId(entity.getId());
    model.setVersion(entity.getVersion());
    model.setApplicantId(entity.getApplicantId());
    model.setReplyRequest(entity.getReplyRequest());
    model.setReferences(entity.getReferences());
    model.setDeviceId(entity.getDeviceId());
    model.setAcceptorId(entity.getAcceptorId());
    model.setApplicantViewed(entity.getApplicantViewed());
    model.setCreatedAt(entity.getCreatedAt());
    model.setLastModifiedAt(entity.getLastModifiedAt());

    return model;
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<ResourceRequestView> toModel(List<ResourceRequest> entities) {
    List<ResourceRequestView> models = Lists.newArrayList();

    Consumer<ResourceRequest> consumer = request -> models.add(toModel(request));

    entities.stream().forEach(consumer);

    return models;
  }
}
