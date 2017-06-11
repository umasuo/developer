package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.ResourceRequestDraft;
import com.umasuo.developer.application.dto.ResourceRequestView;
import com.umasuo.developer.application.service.ResourceRequestApplication;
import com.umasuo.developer.infrastructure.enums.ReplyRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Davis on 17/6/8.
 */
@RestController
public class RequestController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RequestController.class);

  /**
   * The Resource request application.
   */
  @Autowired
  private ResourceRequestApplication resourceRequestApplication;

  /**
   * Create resource request.
   *
   * @param developerId the developer id
   * @param request the resource request
   * @return the resource request draft
   */
  public ResourceRequestView create(@RequestHeader String developerId,
      ResourceRequestDraft request) {
    LOG.info("Enter. applicantId: {}, request: {}.", developerId, request);

    ResourceRequestView result = resourceRequestApplication.create(developerId, request);

    LOG.info("Exit. result: {}.", result);

    return result;
  }

  /**
   * Gets all request for applicant.
   *
   * @param developerId the developer id
   * @return the all request for applicant
   */
  public List<ResourceRequestView> getAllRequestForApplicant(@RequestHeader String developerId) {
    LOG.info("Enter. applicantId: {}.", developerId);

    List<ResourceRequestView> result =
        resourceRequestApplication.getAllRequestForApplicant(developerId);

    LOG.info("Exit. request size: {}.", result.size());

    return result;
  }

  /**
   * Gets all request for acceptor.
   *
   * @param developerId the developer id
   * @return the all request for acceptor
   */
  public List<ResourceRequestView> getAllRequestForAcceptor(@RequestHeader String developerId) {
    LOG.info("Enter. acceptorId: {}.", developerId);

    List<ResourceRequestView> result =
        resourceRequestApplication.getAllRequestForAcceptor(developerId);

    LOG.info("Exit. request size: {}.", result.size());

    return result;
  }

  /**
   * Reply request resource.
   *
   * @param developerId the developer id
   * @param requestId the request id
   * @param reply the reply
   * @return the resource request draft
   */
  public ResourceRequestView replyRequest(@RequestHeader String developerId, String requestId,
      ReplyRequest reply) {
    LOG.info("Enter. developerId: {}, requestId: {}, reply: {}.", developerId, requestId, reply);

    ResourceRequestView result = resourceRequestApplication
        .replyRequest(developerId, requestId, reply);

    LOG.info("Exit. apply result: {}.", result);

    return result;
  }

  /**
   * Feed back for applicant list.
   *
   * @param developerId the developer id
   * @param requestId the request id
   * @return the list
   */
  public List<ResourceRequestView> feedBackForApplicant(@RequestHeader String developerId,
      List<String> requestId) {
    LOG.info("Enter. developerId: {}, requestId: {}.", developerId, requestId);

    List<ResourceRequestView> result = resourceRequestApplication
        .feedBackForApplicant(developerId, requestId);

    LOG.info("Exit. feedBack size: {}.", result.size());

    return result;
  }

  /**
   * Acceptor feedback request.
   *
   * @param developerId the developer id
   * @param requestId the request id
   * @return the list
   */
  public List<ResourceRequestView> feedBackForAcceptor(@RequestHeader String developerId,
      List<String> requestId) {
    LOG.info("Enter. developerId: {}, requestId: {}.", developerId, requestId);

    List<ResourceRequestView> result = resourceRequestApplication
        .feedBackForAcceptor(developerId, requestId);

    LOG.info("Exit. feedBack size: {}.", result.size());

    return result;
  }
}
