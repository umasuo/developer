package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.mapper.MailMessageMapper;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;
import com.umasuo.developer.infrastructure.enums.AccountStatus;
import com.umasuo.developer.infrastructure.util.RedisKeyUtil;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
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

  private static final String VERIFY_SUBJECT = "Please verify your email address.";

  private static final String RESET_SUBJECT = "Please reset password.";

  private static long VERIFY_EXPIRE_TIME = 30 * 60 * 60 * 1000L;

  private static long RESET_EXPIRE_TIME = 30 * 60 * 60 * 1000L;

  private static TimeUnit TIME_UTIL = TimeUnit.MILLISECONDS;

  /**
   * redis ops. cache cluster should be used.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  @Autowired
  private transient DeveloperService developerService;

  @Autowired
  private transient JavaMailSender javaMailSender;

  /**
   * 发送验证邮件。
   * 使用Async实现异步调用，之后考虑使用其它方法实现。
   */
  @Async
  public void sendVerificationEmail(String developerId, String email) {
    LOG.debug("Enter. developerId: {}, email: {}.", developerId, email);

    String verificationCode = RandomStringUtils.randomAlphanumeric(CODE_LENGTH);

    String message = MailMessageMapper.createMessage(developerId, verificationCode);

    SimpleMailMessage mailMessage = MailMessageMapper.build(email, VERIFY_SUBJECT, message);

    javaMailSender.send(mailMessage);

    redisTemplate.opsForValue()
        .set(String.format(RedisKeyUtil.VERIFY_KEY_FORMAT, developerId), verificationCode,
            VERIFY_EXPIRE_TIME, TIME_UTIL);
    LOG.debug("Exit.");
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

    String key = String.format(RedisKeyUtil.VERIFY_KEY_FORMAT, developerId);
    String requestCode = redisTemplate.opsForValue().get(key).toString();
    if (!code.equals(requestCode)) {
      LOG.debug("VerificationCode is not match");
      throw new ParametersException("VerificationCode is not match");
    }
    developer.setStatus(AccountStatus.VERIFIED);
    developerService.save(developer);

    redisTemplate.delete(key);
  }

  /**
   * 发送重置密码的token到开发者邮箱。
   */
  public void sendResetToken(String email) {
    LOG.debug("Enter. email: {}.", email);

    Developer developer = developerService.getWithEmail(email);

    String resetToken = RandomStringUtils.randomAlphanumeric(CODE_LENGTH);

    // TODO: 17/7/3
    String message = "" + resetToken;

    SimpleMailMessage mailMessage = MailMessageMapper.build(email, RESET_SUBJECT, message);

    javaMailSender.send(mailMessage);

    redisTemplate.opsForValue().
        set(String.format(RedisKeyUtil.RESET_KEY_FORMAT, developer.getId()), resetToken,
            RESET_EXPIRE_TIME, TIME_UTIL);
  }

  @Async
  public void resendVerifyEmail(String id) {
    LOG.debug("Enter. id: {}.", id);

    Developer developer = developerService.get(id);

    if (developer == null) {
      LOG.debug("Can not find developer: {}.", id);
      throw new NotExistException("Developer not exist");
    }

    sendVerificationEmail(id, developer.getEmail());
    LOG.debug("Exit.");
  }
}
