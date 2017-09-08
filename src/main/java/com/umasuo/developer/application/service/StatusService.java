package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.AuthStatus;
import com.umasuo.developer.application.dto.DeveloperSession;
import com.umasuo.developer.infrastructure.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * For check login status, developer account status.s
 */
@Service
public class StatusService {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(StatusService.class);

  /**
   * redis ops. cache cluster should be used.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * 检查用户的权限状态。
   * 1，检查ID与token中的ID是否一致
   * 2，检查用户是否登陆
   * 3，登陆是否在有效期内
   *
   * @param id       开发者ID
   * @param tokenStr 传入的token string
   * @return 权限状态
   */
  public AuthStatus checkAuthStatus(String id, String tokenStr) {
    LOGGER.debug("CheckSignInStatus: id: {}", id);
    AuthStatus authStatus = new AuthStatus();

    authStatus.setDeveloperId(id);

    String key = String.format(RedisKeyUtil.DEVELOPER_KEY_FORMAT, id);
    DeveloperSession session =
        (DeveloperSession) redisTemplate.opsForHash().get(key, RedisKeyUtil.DEVELOPER_SESSION_KEY);
    if (session == null) {
      authStatus.setLogin(false);
      return authStatus;
    }

    //todo be careful, keep all machine in the same.
    long lifeTime = session.getLastActiveTime() + DeveloperSession.EXPIRE_IN;
    long curTime = System.currentTimeMillis();
    if (curTime > lifeTime) {
      authStatus.setLogin(false);
      return authStatus;
    }

    authStatus.setLogin(true);

    // 设置过期时间
    redisTemplate.expire(key, DeveloperSession.EXPIRE_IN, TimeUnit.MILLISECONDS);

    session.setLastActiveTime(curTime);

    redisTemplate.boundHashOps(key).put(RedisKeyUtil.DEVELOPER_SESSION_KEY, session);

    return authStatus;
  }

}
