package com.umasuo.developer.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.Token;
import com.umasuo.developer.domain.exception.NotLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * for check login status, developer account status.s
 */
@Service
public class StatusService {

  /**
   * logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(StatusService.class);

  /**
   * redis ops. cache cluster should be used.
   */
  @Autowired
  private transient RedisOperations redisOperations;

  /**
   * JWT(json web token) update
   */
  @Autowired
  private transient JwtUtil jwtUtil;

  /**
   * check login status. for auth check.
   *
   * @param id
   * @return boolean
   */
  public boolean checkLoginStatus(String id) {
    LOGGER.debug("CheckLoginStatus: id: {}", id);

    Token token = (Token) redisOperations.opsForHash().get(id, SignInService.SIGN_IN_CACHE_KEY);
    Assert.notNull(token);

    //todo be careful, keep all machine in the same.
    long lifeTime = token.getGenerateTime() + token.getExpiresIn();
    long curTime = System.currentTimeMillis();
    if (curTime < lifeTime) {
      throw new NotLoginException("Login status expired.");
    }
    return true;
  }
}
