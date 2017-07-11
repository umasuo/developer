package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.DeviceDefinitionView;
import com.umasuo.developer.application.dto.UserPermissionRequest;
import com.umasuo.exception.NotExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
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
   * User service uri.
   */
  @Value("${user.service.uri:http://user/}")
  private transient String userUrl;

  /**
   * device-definition service uri.
   */
  @Value("${product.service.uri:http://product/}")
  private transient String productUrl;

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

  /**
   * Gets device definition.
   *
   * @param deviceDefinitionId the device definition id
   * @param developerId the developer id
   * @return the device definition
   */
  public DeviceDefinitionView getDeviceDefinition(String deviceDefinitionId, String developerId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    HttpEntity entity = new HttpEntity(headers);

    String url = productUrl + deviceDefinitionId;
    DeviceDefinitionView result = null;
    try {

      ResponseEntity<DeviceDefinitionView> response = restTemplate
          .exchange(url, HttpMethod.GET, entity, DeviceDefinitionView.class);

       result = response.getBody();
    } catch (InvalidMediaTypeException invalidMediaTypeException) {
      LOG.info("Can not find DeviceDefinition: ", invalidMediaTypeException);
      throw new NotExistException("Can not find DeviceDefinition");
    }

    return result;
  }
}