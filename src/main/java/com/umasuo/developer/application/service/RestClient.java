package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.UserPermissionRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * The type Rest client.
 */
@Component
public class RestClient {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);

  /**
   * Data-definition service uri.
   */
  @Value("${user.service.uri:http://user/}")
  private transient String userUrl;

  /**
   * RestTemplate.
   */
  private transient RestTemplate restTemplate = new RestTemplate();

  /**
   * Forward UserPermissionRequest.
   *
   * @param request the request
   */
  public void forwardUserRequest(UserPermissionRequest request) {
    // TODO: 17/6/11
    LOG.info("Enter. request: {}.", request);

    restTemplate.postForEntity(userUrl, request, null);

    LOG.info("Exit.");
  }
}