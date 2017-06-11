package com.umasuo.developer.infrastructure.validator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umasuo.developer.domain.model.ResourceRequest;
import com.umasuo.exception.AuthFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/11.
 */
public final class FeedBackValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(FeedBackValidator.class);

  /**
   * Instantiates a new Feed back validator.
   */
  private FeedBackValidator() {
  }

  /**
   * Check if the acceptor have authentication to feedback those request.
   *
   * @param developerId developer id
   * @param requests ResourceRequest found from database
   */
  public static void validateAcceptorFeedBackAuth(String developerId,
      List<ResourceRequest> requests) {
    Map<String, String> developerRequestMap = Maps.newHashMap();
    Consumer<ResourceRequest> consumer =
        request -> developerRequestMap.put(request.getId(), request.getAcceptorId());
    requests.stream().forEach(consumer);

    validateFeedBackAuth(developerId, developerRequestMap);
  }

  /**
   * Check if the applicant have authentication to feedback those request.
   *
   * @param developerId developer id
   * @param requests ResourceRequest found from database
   */
  public static void validateApplicantFeedBackAuth(String developerId,
      List<ResourceRequest> requests) {
    // 检查开发者是否有权限反馈
    Map<String, String> developerRequestMap = Maps.newHashMap();
    Consumer<ResourceRequest> consumer =
        request -> developerRequestMap.put(request.getId(), request.getApplicantId());
    requests.stream().forEach(consumer);

    validateFeedBackAuth(developerId, developerRequestMap);
  }

  /**
   * Check if the developer have the auth to feedback the request.
   *
   * @param developerId the developer id
   * @param developerRequestMap the map
   */
  private static void validateFeedBackAuth(String developerId,
      Map<String, String> developerRequestMap) {
    List<String> notAuthDevelopers = Lists.newArrayList();
    Consumer<Entry<String, String>> entryConsumer = entry -> {
      if (!entry.getValue().equals(developerId)) {
        notAuthDevelopers.add(entry.getKey());
      }
    };
    developerRequestMap.entrySet().stream().forEach(entryConsumer);

    if (!notAuthDevelopers.isEmpty()) {
      LOG.debug("Developer: {} do not have auth to feedback request: {}.",
          developerId, notAuthDevelopers);
      throw new AuthFailedException("Developer do not have authorization to feedback request");
    }
  }
}
