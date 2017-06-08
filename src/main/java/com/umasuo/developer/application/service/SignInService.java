package com.umasuo.developer.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.Token;
import com.umasuo.authentication.TokenType;
import com.umasuo.developer.application.dto.SignInResult;
import com.umasuo.developer.application.dto.mapper.DeveloperMapper;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;
import com.umasuo.developer.infrastructure.util.PasswordUtil;
import com.umasuo.exception.PasswordErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by umasuo on 17/3/6.
 * Login service for login and keep login status.
 */
@Service
public class SignInService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignInService.class);

  /**
   * the key in mapper which in cache.
   */
  public final static String SIGN_IN_CACHE_KEY = "signin";

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
    Token token = jwtUtil.parseToken(tokenString);
    //cache the sigin result
    redisTemplate.boundHashOps(id).put(SIGN_IN_CACHE_KEY, token);
  }
}
