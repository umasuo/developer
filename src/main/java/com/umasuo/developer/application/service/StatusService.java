package com.umasuo.developer.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.Scope;
import com.umasuo.authentication.Token;
import com.umasuo.developer.domain.exception.NotSignInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

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
  private transient RedisTemplate redisTemplate;

  /**
   * JWT(json web token) update
   */
  @Autowired
  private transient JwtUtil jwtUtil;

  /**
   * check login status. for auth check.
   * this can only be accessed in internal net work.
   *
   * @param id
   * @return boolean
   */
  public boolean checkLoginStatus(String id) {
    LOGGER.debug("CheckLoginStatus: id: {}", id);

    Token token = (Token) redisTemplate.opsForHash().get(id, SignInService.SIGN_IN_CACHE_KEY);
    if (token == null) {
      return false;
    }

    //todo be careful, keep all machine in the same.
    long lifeTime = token.getGenerateTime() + token.getExpiresIn();
    long curTime = System.currentTimeMillis();
    if (curTime > lifeTime) {
      return false;
    }
    return true;
  }

  /**
   * check if the developer is login and has the right scope.
   * this can only be accessed in internal net work.
   *
   * @param id
   * @param scopes
   * @return
   */
  public boolean checkSignInAndScope(String id, List<Scope> scopes) {
    //TODO finish later.
    return true;
  }
}
