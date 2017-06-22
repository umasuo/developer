package com.umasuo.developer.application.rest;

import static com.umasuo.developer.infrastructure.Router.ACCEPTOR_REQUEST_ROOT;
import static com.umasuo.developer.infrastructure.Router.ACCEPTOR_REQUEST_WITH_ID;
import static com.umasuo.developer.infrastructure.Router.APPLICANT_REQUEST_ROOT;

import com.umasuo.developer.application.dto.ResourceRequestDraft;
import com.umasuo.developer.application.dto.ResourceRequestView;
import com.umasuo.developer.application.service.ResourceRequestApplication;
import com.umasuo.developer.infrastructure.enums.ReplyRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Davis on 17/6/8.
 */
@CrossOrigin
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
  @PostMapping(APPLICANT_REQUEST_ROOT)
  public ResourceRequestView create(@RequestHeader("developerId") String developerId,
      @RequestBody ResourceRequestDraft request) {
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
  @GetMapping(APPLICANT_REQUEST_ROOT)
  public List<ResourceRequestView> getAllRequestForApplicant(
      @RequestHeader("developerId") String developerId) {
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
  @GetMapping(ACCEPTOR_REQUEST_ROOT)
  public List<ResourceRequestView> getAllRequestForAcceptor(
      @RequestHeader("developerId") String developerId) {
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
  @PutMapping(ACCEPTOR_REQUEST_WITH_ID)
  public ResourceRequestView replyRequest(@RequestHeader("developerId") String developerId,
      @PathVariable("id") String requestId, @RequestBody ReplyRequest reply) {
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
  @PutMapping(APPLICANT_REQUEST_ROOT)
  public List<ResourceRequestView> feedBackForApplicant(
      @RequestHeader("developerId") String developerId, @RequestBody List<String> requestId) {
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
  @PutMapping(ACCEPTOR_REQUEST_ROOT)
  public List<ResourceRequestView> feedBackForAcceptor(
      @RequestHeader("developerId") String developerId, @RequestBody List<String> requestId) {
    LOG.info("Enter. developerId: {}, requestId: {}.", developerId, requestId);

    List<ResourceRequestView> result = resourceRequestApplication
        .feedBackForAcceptor(developerId, requestId);

    LOG.info("Exit. feedBack size: {}.", result.size());

    return result;
  }
}
