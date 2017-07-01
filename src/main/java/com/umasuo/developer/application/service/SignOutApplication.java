package com.umasuo.developer.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by umasuo on 17/3/6.
 * sign out service.
 */
@Service
public class SignOutApplication {

  /**
   * logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignInApplication.class);

  /**
   * redis ops. cache cluster should be used.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * JWT(json web token) update
   */
  @Autowired
  private transient JwtUtil jwtUtil;

  /**
   * sign out. When sign out, clear the cache.
   *
   * @param tokenString token.
   */
  public void signOut(String tokenString) {
    LOGGER.debug("SignOut: token: {}", tokenString);

    //parse the token ,to see if the token is legal
    // todo 考虑去掉JWT的格式
    Token token = jwtUtil.parseToken(tokenString);
    clearCache(token);

    LOGGER.debug("SignOut: token: {}", token);
  }

  /**
   * clear the cache.
   *
   * @param token Token
   */
  private void clearCache(Token token) {
    String id = token.getSubjectId();
    Assert.notNull(id);
    // 清理该开发者的token
    redisTemplate.delete(id);
  }
}
