package com.umasuo.developer.application.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umasuo.developer.application.dto.ResourceRequestView;
import com.umasuo.developer.application.dto.ResourceRequestDraft;
import com.umasuo.developer.application.dto.mapper.ResourcePermissionMapper;
import com.umasuo.developer.application.dto.mapper.ResourceRequestMapper;
import com.umasuo.developer.domain.model.ResourcePermission;
import com.umasuo.developer.domain.model.ResourceRequest;
import com.umasuo.developer.domain.service.ResourcePermissionService;
import com.umasuo.developer.domain.service.ResourceRequestService;
import com.umasuo.developer.infrastructure.enums.ReplyRequest;
import com.umasuo.exception.AuthFailedException;
import com.umasuo.exception.NotExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * ResourceRequest application.
 *
 * Created by Davis on 17/6/9.
 */
@Service
public class ResourceRequestApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ResourceRequestApplication.class);

  /**
   * The Resource request service.
   */
  @Autowired
  private ResourceRequestService resourceRequestService;

  /**
   * The Resource permission servce.
   */
  @Autowired
  private ResourcePermissionService resourcePermissionServce;

  /**
   * Create resource request.
   *
   * @param applicantId the applicant id
   * @param request the request
   * @return the resource request view
   */
  public ResourceRequestView create(String applicantId, ResourceRequestDraft request) {
    LOG.debug("Enter. applicantId: {}, request: {}.", applicantId, request);

    // TODO: 17/6/9 判断acceptor是否拥有该device和reference

    ResourceRequest resourceRequest = ResourceRequestMapper.toEntity(applicantId, request);
    ResourceRequest savedRequest = resourceRequestService.save(resourceRequest);

    ResourceRequestView result = ResourceRequestMapper.toModel(savedRequest);

    LOG.debug("Exit. saved resourceRequest: {}.", result);

    return result;
  }

  /**
   * Gets all request for applicant.
   *
   * @param applicantId the applicant id
   * @return the all request for applicant
   */
  public List<ResourceRequestView> getAllRequestForApplicant(String applicantId) {
    LOG.debug("Enter. applicantId: {}.", applicantId);

    List<ResourceRequest> requests = resourceRequestService.getAllRequestForApplicant(applicantId);
    List<ResourceRequestView> result = ResourceRequestMapper.toModel(requests);

    LOG.debug("Exit. applicant request size: {}.", result.size());

    return result;
  }

  /**
   * Gets all request for acceptor.
   *
   * @param acceptorId the acceptor id
   * @return the all request for acceptor
   */
  public List<ResourceRequestView> getAllRequestForAcceptor(String acceptorId) {
    LOG.debug("Enter. acceptorId: {}.", acceptorId);

    List<ResourceRequest> requests = resourceRequestService.getAllRequestForAcceptor(acceptorId);
    List<ResourceRequestView> result = ResourceRequestMapper.toModel(requests);

    LOG.debug("Exit. acceptor request size: {}.", result.size());

    return result;
  }

  /**
   * Reply request.
   *
   * @param developerId the developer id
   * @param requestId the request id
   * @param reply the reply
   * @return the resource request view
   */
  public ResourceRequestView replyRequest(String developerId, String requestId,
      ReplyRequest reply) {
    LOG.debug("Enter. developerId: {}, requestId: {}, reply: {}.", developerId, requestId, reply);

    ResourceRequest request = resourceRequestService.findOne(requestId);
    if (request == null) {
      LOG.debug("Can not find this resource request: {}.", requestId);
      throw new NotExistException("ResourceRequest not exist");
    }

    if (!request.getAcceptorId().equals(developerId)) {
      LOG.debug("Developer: {} can not handle this request: {}.", developerId, requestId);
      throw new AuthFailedException("Developer do not have authorization to handle request");
    }

    handlerReply(request, reply);

    ResourceRequest updatedRequest = resourceRequestService.save(request);
    ResourceRequestView result = ResourceRequestMapper.toModel(updatedRequest);

    LOG.debug("Exit. reply result: {}.", result);

    return result;
  }

  /**
   * Handle reply.
   *
   * @param request the ResourceRequest
   * @param reply the ReplyRequest
   */
  private void handlerReply(ResourceRequest request, ReplyRequest reply) {
        request.setReplyRequest(reply);
    switch (reply) {
      case AGREE:
        ResourcePermission permission = ResourcePermissionMapper.build(request);
        resourcePermissionServce.save(permission);
        break;
      case DISAGREE:
        // TODO: 17/6/9 取消权限，需要把permission删除，并把对应的user权限也删除
        break;
      default:
        break;
    }
  }

  /**
   * Feed back for applicant list.
   *
   * @param developerId the developer id
   * @param requestId the request id
   * @return the list
   */
  public List<ResourceRequestView> feedBackForApplicant(String developerId,
      List<String> requestId) {
    LOG.debug("Enter. developerId: {}, requestId: {}.", developerId, requestId);

    List<ResourceRequest> requests = resourceRequestService.findAll(requestId);
    validateNotExitRequest(requestId, requests);
    validateFeedBackAuth(developerId, requests);

    Consumer<ResourceRequest> feedBackConsumer = request -> request.setApplicantViewed(true);
    requests.stream().forEach(feedBackConsumer);
    List<ResourceRequest> feedBackedRequests = resourceRequestService.saveAll(requests);

    List<ResourceRequestView> result = ResourceRequestMapper.toModel(feedBackedRequests);

    LOG.debug("Exit. feedback size: {}.", result.size());

    return result;
  }

  /**
   * Check if the developer have authentication to feedback those request.
   * @param developerId developer id
   * @param requests ResourceRequest found from database
   */
  private void validateFeedBackAuth(String developerId, List<ResourceRequest> requests) {
    // 检查开发者是否有权限反馈
    Map<String, String> developerRequestMap = Maps.newHashMap();
    Consumer<ResourceRequest> consumer =
        request -> developerRequestMap.put(request.getId(), request.getApplicantId());
    requests.stream().forEach(consumer);

    List<String> notAuthDevelopers = Lists.newArrayList();
    Consumer<Entry<String, String>> entryConsumer = entry -> {
      if (!entry.getValue().equals(developerId)) {
        notAuthDevelopers.add(entry.getKey());
      }
    };
    developerRequestMap.entrySet().stream().forEach(entryConsumer);

    if (notAuthDevelopers.size() > 0) {
      LOG.debug("Developer: {} do not have auth to feedback request: {}.", developerId,
          notAuthDevelopers);
      throw new AuthFailedException("Developer do not have authorization to feedback request");
    }
  }

  /**
   * Check if the request exist.
   *
   * @param requestId the ResourceRequest id list
   * @param requests the ResourceRequest found from database by the id list
   */
  private void validateNotExitRequest(List<String> requestId, List<ResourceRequest> requests) {
    // 检查要反馈的请求是否存在
    List<String> existRequestId =
        requests.stream().map(ResourceRequest::getId).collect(Collectors.toList());

    requestId.removeAll(existRequestId);
    if (requestId.size() > 0) {
      LOG.debug("Request: {} not exist.", requestId);
      throw new NotExistException("Request not exist: " + requestId);
    }
  }
}
