package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.application.dto.action.ResetPassword;
import com.umasuo.developer.application.dto.mapper.DeveloperMapper;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;
import com.umasuo.developer.infrastructure.util.PasswordUtil;
import com.umasuo.developer.infrastructure.util.RedisKeyUtil;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Developer application.
 */
@Service
public class DeveloperApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperApplication.class);

  /**
   * Redis client.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Developer Service.
   */
  @Autowired
  private transient DeveloperService developerService;

  /**
   * Reset password.
   */
  public void resetPassword(ResetPassword resetRequest) {
    LOGGER.debug("Enter.");

    String developerId = resetRequest.getDeveloperId();

    String key = String.format(RedisKeyUtil.RESET_KEY_FORMAT, developerId);

    String requestToken = (String) redisTemplate.opsForValue().get(key);

    if (StringUtils.isBlank(requestToken)) {
      LOGGER.debug("Can not find reset password code for developer: {}.", developerId);
      throw new NotExistException("Reset password code not exist or expired");
    }

    String token = resetRequest.getToken();
    if (!token.equals(requestToken)) {
      LOGGER.debug("Reset password token is out of time or not exist.");
      throw new ParametersException("Token not correct");
    }

    Developer developer = developerService.get(developerId);

    developer.setPassword(PasswordUtil.hashPassword(resetRequest.getNewPassword()));

    developerService.save(developer);

    redisTemplate.delete(key);
  }

  /**
   * Get all developers.
   */
  public List<DeveloperView> getAllDevelopers() {
    LOGGER.debug("Enter.");

    List<Developer> developers = developerService.getAllDevelopers();

    List<DeveloperView> result = DeveloperMapper.toView(developers);

    LOGGER.debug("Exit. developer size: {}.", result.size());

    return result;
  }
}
