package com.umasuo.developer.application.service;

import static com.umasuo.developer.infrastructure.update.UpdateActionUtils.RESET_PASSWORD;

import com.umasuo.developer.application.dto.action.ResetPassword;
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

/**
 * Created by Davis on 17/7/3.
 */
@Service(RESET_PASSWORD)
public class DeveloperApplication{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(DeveloperApplication.class);

  @Autowired
  private transient RedisTemplate redisTemplate;

  @Autowired
  private transient DeveloperService developerService;

  public void resetPassword(ResetPassword resetRequest) {
    LOG.debug("Enter.");

    String developerId = resetRequest.getDeveloperId();
    String token = resetRequest.getToken();

    String key = String.format(RedisKeyUtil.RESET_KEY_FORMAT, developerId);

    String requestToken = (String) redisTemplate.opsForValue().get(key);

    if (StringUtils.isBlank(requestToken)) {
      LOG.debug("Can not find reset password code for developer: {}.", developerId);
      throw new NotExistException("Reset password code not exist or expired");
      // TODO: 17/7/5 返回一个验证码过期的跳转链接
    }

    if (!token.equals(requestToken)) {
      LOG.debug("Reset password token is out of time or not exist.");
      throw new ParametersException("Token not correct");
      // TODO: 17/7/5 返回一个验证码不对的跳转链接
    }

    Developer developer = developerService.get(developerId);

    developer.setPassword(PasswordUtil.hashPassword(resetRequest.getNewPassword()));

    developerService.save(developer);

    redisTemplate.delete(key);
    // TODO: 17/7/5 返回一个成功的跳转链接
  }
}
