package com.umasuo.developer.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.Scope;
import com.umasuo.authentication.Token;
import com.umasuo.developer.application.dto.AuthStatus;
import com.umasuo.developer.infrastructure.configuration.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * for check login status, developer account status.s
 */
@Service
public class StatusService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(StatusService.class);

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
   * JWT(json web token) update
   */
  @Autowired
  private transient AppConfig config;

  /**
   * 检查用户的权限状态。
   * 1，检查ID与token中的ID是否一致
   * 2，检查用户是否登陆
   * 3，登陆是否在有效期内
   * @param id 开发者ID
   * @param tokenStr 传入的token string
   * @return 权限状态
   */
  public AuthStatus checkAuthStatus(String id, String tokenStr) {
    logger.debug("CheckSignInStatus: id: {}", id);
    AuthStatus authStatus = new AuthStatus();

    Token tokenInput = JwtUtil.parseToken(config.getSecret(), tokenStr);
    if (!tokenInput.getSubjectId().equals(id)) {
      //检查传入的token是否属于该开发者
      authStatus.setLogin(false);
      return authStatus;
    }
    authStatus.setDeveloperId(id);

    Token token = (Token) redisTemplate.opsForHash().get(id, SignInService.SIGN_IN_CACHE_KEY);
    if (token == null) {
      authStatus.setLogin(false);
      return authStatus;
    }

    //todo be careful, keep all machine in the same.
    long lifeTime = token.getGenerateTime() + token.getExpiresIn();
    long curTime = System.currentTimeMillis();
    if (curTime > lifeTime) {
      authStatus.setLogin(false);
      return authStatus;
    }
    authStatus.setLogin(true);

    return authStatus;
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
