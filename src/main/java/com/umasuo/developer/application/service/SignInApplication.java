package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.DeveloperSession;
import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.application.dto.SignInResult;
import com.umasuo.developer.application.dto.mapper.DeveloperMapper;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;
import com.umasuo.developer.infrastructure.util.PasswordUtil;
import com.umasuo.developer.infrastructure.util.RedisKeyUtil;
import com.umasuo.exception.PasswordErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Sign in application.
 * Login service for login and keep login status.
 */
@Service
public class SignInApplication {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignInApplication.class);

  /**
   * Session expire time.
   */
  public static final long EXPIRE_TIME = 4 * 60 * 60 * 1000L;

  /**
   * customer service.
   */
  @Autowired
  private transient DeveloperService developerService;

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * login with email and password.
   *
   * @param email    String
   * @param password String
   * @return LoginResult
   */
  public SignInResult signInWithEmail(String email, String password) {
    LOGGER.debug("Enter. email: {}", email);
    Developer developer = developerService.getWithEmail(email);

    boolean pwdResult = PasswordUtil.checkPassword(password, developer.getPassword());
    if (!pwdResult) {
      throw new PasswordErrorException("password or email not correct.");
    }

    //todo 如果采用https 可以采用JWT，免去session的检查必要，如果采用http，可以考虑去掉jwt
    String token = UUID.randomUUID().toString();

    SignInResult result = new SignInResult(DeveloperMapper.toView(developer), token);

    cacheSignInStatus(result.getDeveloperView(), token);

    LOGGER.debug("Exit: result: {}", result);
    return result;
  }

  /**
   * cache sign in status to cache.
   * each developer has an mapper in cache for keep status data.
   *
   * @param token         developer id
   * @param developerView
   */
  private void cacheSignInStatus(DeveloperView developerView, String token) {
    LOGGER.debug("Enter.");
    //todo cache key 的设置
    //cache the sign in result
    String key = String.format(RedisKeyUtil.DEVELOPER_KEY_FORMAT, token);
    DeveloperSession session = new DeveloperSession(developerView, token);
    redisTemplate.boundHashOps(key).put(RedisKeyUtil.DEVELOPER_SESSION_KEY, session);
    redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.MILLISECONDS);
    LOGGER.debug("Exit.");
  }
}
