package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.DeveloperSession;
import com.umasuo.developer.infrastructure.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Sign out application.
 * sign out service.
 */
@Service
public class SignOutApplication {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignInApplication.class);

  /**
   * redis ops. cache cluster should be used.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Sign out. When sign out, clear the cache.
   *
   * @param token token.
   */
  public void signOut(String id, String token) {
    LOGGER.debug("Enter. id: {}, token: {}", id, token);
    assert id != null;
    assert token != null;

    String key = String.format(RedisKeyUtil.DEVELOPER_KEY_FORMAT, id);
    DeveloperSession session = (DeveloperSession) redisTemplate.opsForHash().get(key, RedisKeyUtil.DEVELOPER_SESSION_KEY);
    if (session != null && token.equals(session.getToken())) {
      redisTemplate.delete(key);
    }
    LOGGER.debug("Exit.");
  }

}
