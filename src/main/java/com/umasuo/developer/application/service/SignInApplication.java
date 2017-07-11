package com.umasuo.developer.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.Token;
import com.umasuo.authentication.TokenType;
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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by umasuo on 17/3/6.
 * Login service for login and keep login status.
 */
@Service
public class SignInApplication {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignInApplication.class);

  public static final long EXPIRE_TIME = 4 * 60 * 60 * 1000L;

  /**
   * JWT(json web token) update
   */
  @Autowired
  private transient JwtUtil jwtUtil;

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
    logger.debug("SignInWithEmail: email: {}", email);
    Developer developer = developerService.getWithEmail(email);

    boolean pwdResult = PasswordUtil.checkPassword(password, developer.getPassword());
    if (!pwdResult) {
      throw new PasswordErrorException("password or email not correct.");
    }

    //todo 如果采用https 可以采用JWT，免去session的检查必要，如果采用http，可以考虑去掉jwt
    String token = jwtUtil.generateToken(TokenType.DEVELOPER, developer.getId(), jwtUtil.getExpiresIn(),
        new ArrayList<>());

    SignInResult result = new SignInResult(DeveloperMapper.toModel(developer), token);

    cacheSignInStatus(developer.getId(), token);

    logger.debug("SignInWithEmail: result: {}", result);
    return result;
  }

  /**
   * cache sign in status to cache.
   * each developer has an mapper in cache for keep status data.
   *
   * @param id          developer id
   * @param tokenString token string
   */
  private void cacheSignInStatus(String id, String tokenString) {
    logger.debug("Enter.");
    Token token = jwtUtil.parseToken(tokenString);
    //todo cache key 的设置
    //cache the sigin result
    String key = String.format(RedisKeyUtil.DEVELOPER_KEY_FORMAT, id);
    redisTemplate.boundHashOps(key).put(RedisKeyUtil.SIGN_IN_CACHE_KEY, token);
    redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.MILLISECONDS);
    logger.debug("Exit.");
  }
}
