package com.umasuo.developer.domain.service;

import com.umasuo.developer.domain.model.ResourceRequest;
import com.umasuo.developer.infrastructure.repository.ResourceRequestRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/6/9.
 */
@Service
public class ResourceRequestService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ResourceRequestService.class);

  /**
   * The Repository.
   */
  @Autowired
  private ResourceRequestRepository repository;

  /**
   * Save resource request.
   *
   * @param resourceRequest the resource request
   * @return the resource request
   */
  public ResourceRequest save(ResourceRequest resourceRequest) {
    LOG.debug("Enter. resourceRequest: {}.", resourceRequest);

    ResourceRequest savedRequest = repository.save(resourceRequest);

    LOG.debug("Exit. savedRequest: {}.", savedRequest);

    return savedRequest;
  }

  /**
   * Gets all request for applicant.
   *
   * @param applicantId the applicant id
   * @return the all request for applicant
   */
  public List<ResourceRequest> getAllRequestForApplicant(String applicantId) {
    LOG.debug("Enter. applicantId: {}.", applicantId);

    List<ResourceRequest> requests = repository.findByApplicantId(applicantId);

    LOG.debug("Exit. applicant request size: {}.", requests.size());

    return requests;
  }

  /**
   * Gets all request for acceptor.
   *
   * @param acceptorId the acceptor id
   * @return the all request for acceptor
   */
  public List<ResourceRequest> getAllRequestForAcceptor(String acceptorId) {
    LOG.debug("Enter. acceptorId: {}.", acceptorId);

    List<ResourceRequest> requests = repository.findByAcceptorId(acceptorId);

    LOG.debug("Exit. acceptor request size: {}.", requests.size());

    return requests;
  }

  /**
   * Find one resource request.
   *
   * @param requestId the request id
   * @return the resource request
   */
  public ResourceRequest findOne(String requestId) {
    LOG.debug("Enter. requestId: {}.", requestId);

    ResourceRequest request = repository.findOne(requestId);

    LOG.debug("Exit. request: {}.", request);

    return request;
  }

  /**
   * Find all by id.
   *
   * @param requestId the request id
   * @return the list
   */
  public List<ResourceRequest> findAll(List<String> requestId) {
    LOG.debug("Enter. requestId: {}.", requestId);

    List<ResourceRequest> requests = repository.findAll(requestId);

    LOG.debug("Exit. requests size: {}.", requests.size());

    return requests;
  }

  /**
   * Save all request.
   *
   * @param requests the requests
   * @return the list
   */
  public List<ResourceRequest> saveAll(List<ResourceRequest> requests) {
    LOG.debug("Enter. request size: {}.", requests.size());

    List<ResourceRequest> result = repository.save(requests);

    LOG.debug("Exit.");

    return result;
  }
}
