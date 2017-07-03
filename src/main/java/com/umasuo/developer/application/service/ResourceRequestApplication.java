package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.DeviceDefinitionView;
import com.umasuo.developer.application.dto.ResourceRequestDraft;
import com.umasuo.developer.application.dto.ResourceRequestView;
import com.umasuo.developer.application.dto.mapper.ResourcePermissionMapper;
import com.umasuo.developer.application.dto.mapper.ResourceRequestMapper;
import com.umasuo.developer.domain.model.ResourcePermission;
import com.umasuo.developer.domain.model.ResourceRequest;
import com.umasuo.developer.domain.service.ResourcePermissionService;
import com.umasuo.developer.domain.service.ResourceRequestService;
import com.umasuo.developer.infrastructure.enums.ReplyRequest;
import com.umasuo.developer.infrastructure.validator.DeviceDefinitionValidator;
import com.umasuo.developer.infrastructure.validator.ExistRequestValidator;
import com.umasuo.developer.infrastructure.validator.FeedBackValidator;
import com.umasuo.exception.AuthFailedException;
import com.umasuo.exception.NotExistException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * ResourceRequest application.
 *
 * Created by Davis on 17/6/9.
 *
 * todo 暂时没用
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
   * The Rest client.
   */
  @Autowired
  private RestClient restClient;

  /**
   * Create resource request.
   *
   * @param applicantId the applicant id
   * @param request the request
   * @return the resource request view
   */
  public ResourceRequestView create(String applicantId, ResourceRequestDraft request) {
    LOG.debug("Enter. applicantId: {}, request: {}.", applicantId, request);

    // 检查acceptor是否拥有该device，已经检查该device是否拥有对应的reference（目前只是data）
    if (StringUtils.isNotBlank(request.getDeviceDefinitionId())) {
    DeviceDefinitionView deviceDefinitionView = restClient
        .getDeviceDefinition(request.getDeviceDefinitionId(), request.getAcceptorId());
      DeviceDefinitionValidator.validate(deviceDefinitionView, request);
    }

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
   * Feed back for applicant.
   *
   * @param developerId the developer id
   * @param requestId the request id
   * @return the list
   */
  public List<ResourceRequestView> feedBackForApplicant(String developerId,
      List<String> requestId) {
    LOG.debug("Enter. developerId: {}, requestId: {}.", developerId, requestId);

    List<ResourceRequest> requests = resourceRequestService.findAll(requestId);
    ExistRequestValidator.validate(requestId, requests);
    FeedBackValidator.validateApplicantFeedBackAuth(developerId, requests);

    Consumer<ResourceRequest> feedBackConsumer = request -> request.setApplicantViewed(true);
    requests.stream().forEach(feedBackConsumer);
    List<ResourceRequest> feedBackedRequests = resourceRequestService.saveAll(requests);

    List<ResourceRequestView> result = ResourceRequestMapper.toModel(feedBackedRequests);

    LOG.debug("Exit. feedback size: {}.", result.size());

    return result;
  }

  /**
   * Feed back for acceptor.
   *
   * @param developerId the developer id
   * @param requestId the request id
   * @return the list
   */
  public List<ResourceRequestView> feedBackForAcceptor(String developerId,
      List<String> requestId) {
    LOG.debug("Enter. developerId: {}, requestId: {}.", developerId, requestId);

    List<ResourceRequest> requests = resourceRequestService.findAll(requestId);
    ExistRequestValidator.validate(requestId, requests);
    FeedBackValidator.validateAcceptorFeedBackAuth(developerId, requests);

    Consumer<ResourceRequest> feedBackConsumer =
        request -> request.setReplyRequest(ReplyRequest.VIEWED);
    requests.stream().forEach(feedBackConsumer);
    List<ResourceRequest> feedBackedRequests = resourceRequestService.saveAll(requests);

    List<ResourceRequestView> result = ResourceRequestMapper.toModel(feedBackedRequests);

    LOG.debug("Exit. feedback size: {}.", result.size());

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
    request.setApplicantViewed(false);
    switch (reply) {
      case AGREE:
        ResourcePermission permission = ResourcePermissionMapper.build(request);
        resourcePermissionServce.save(permission);
        break;
      default:
        break;
    }
  }
}
