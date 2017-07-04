package com.umasuo.developer.domain.service.update;

import static com.umasuo.developer.infrastructure.update.UpdateActionUtils.RESET_PASSWORD;

import com.umasuo.developer.application.dto.action.ResetPassword;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.util.PasswordUtil;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/7/3.
 */
@Service(RESET_PASSWORD)
public class ResetPasswordService implements Updater<Developer, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordService.class);

  @Autowired
  private transient RedisTemplate redisTemplate;

  @Value("${redis.key.reset}")
  private String RESET_VERIFICATION_KEY;

  @Override
  public void handle(Developer developer, UpdateAction updateAction) {

    ResetPassword action = (ResetPassword) updateAction;

    String token = action.getToken();

    String key = String.format(RESET_VERIFICATION_KEY, developer.getId());

    String requestToken = redisTemplate.opsForValue().get(key).toString();

    if (!token.equals(requestToken)) {
      LOG.debug("Reset password token is out of time or not exist.");
      throw new ParametersException("Token not correct");
    }

    developer.setPassword(PasswordUtil.hashPassword(action.getNewPassword()));
    redisTemplate.delete(key);
  }
}
