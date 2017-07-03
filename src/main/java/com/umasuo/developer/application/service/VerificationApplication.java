package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.mapper.MailMessageMapper;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;
import com.umasuo.developer.infrastructure.enums.AccountStatus;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Davis on 17/7/3.
 */
@Service
public class VerificationApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(VerificationApplication.class);

  private static final int CODE_LENGTH = 12;

  private static final String VERIFICATION_KEY = "developer:verification:";

  private static final String subject = "Please verify your email address.";

  /**
   * redis ops. cache cluster should be used.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  @Autowired
  private transient DeveloperService developerService;

  private static long EXPIRE_TIME = 3 * 60 * 60 * 1000L;

  private static TimeUnit TIME_UTIL = TimeUnit.MILLISECONDS;

  @Autowired
  private transient JavaMailSender javaMailSender;

  /**
   * 发送验证邮件。
   */
  public void sendVerificationEmail(String developerId, String email) {
    LOG.debug("Enter. developerId: {}, email: {}.", developerId, email);

    String verificationCode = RandomStringUtils.randomAlphanumeric(CODE_LENGTH);

    String message = MailMessageMapper.createMessage(developerId, verificationCode);

    SimpleMailMessage mailMessage = MailMessageMapper.build(email, subject, message);

    javaMailSender.send(mailMessage);

    redisTemplate.opsForValue()
        .set(createRedisKey(developerId), verificationCode, EXPIRE_TIME, TIME_UTIL);
  }

  /**
   * 验证邮箱。
   */
  public void verifyEmail(String developerId, String code) {
    LOG.debug("Enter. developerId: {}, verificationCode: {}.", developerId, code);
    Developer developer = developerService.get(developerId);
    if (developer == null) {
      LOG.debug("Can not find the developer: {}.", developerId);
      throw new NotExistException("Developer not exist");
    }

    String redisKey = createRedisKey(developerId);
    String requestCode = redisTemplate.opsForValue().get(redisKey).toString();
    if (!code.equals(requestCode)) {
      LOG.debug("VerificationCode is not match");
      throw new ParametersException("VerificationCode is not match");
    }
    developer.setStatus(AccountStatus.VERIFIED);
    developerService.save(developer);

    redisTemplate.delete(redisKey);
  }

  /**
   * Create key for redis.
   */
  private String createRedisKey(String developerId) {
    return VERIFICATION_KEY + developerId;
  }
}
