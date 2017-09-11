package com.umasuo.developer.application.service;

import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;
import com.umasuo.developer.infrastructure.configuration.MailContentBuilder;
import com.umasuo.developer.infrastructure.enums.AccountStatus;
import com.umasuo.developer.infrastructure.util.RedisKeyUtil;
import com.umasuo.exception.ParametersException;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * VerificationApplication.
 */
@Service
public class VerificationApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(VerificationApplication.class);

  /**
   * Length of.
   */
  private static final int CODE_LENGTH = 12;

  /**
   * Verify subject.
   */
  private static final String VERIFY_SUBJECT = "Please verify your email address.";

  /**
   * Reset subject.
   */
  private static final String RESET_SUBJECT = "Please reset password.";

  /**
   * Verify token expire time.
   */
  private static final long VERIFY_EXPIRE_TIME = 30 * 60 * 60 * 1000L;

  /**
   * Reset token expire time.
   */
  private static final long RESET_EXPIRE_TIME = 30 * 60 * 60 * 1000L;

  /**
   * redis ops. cache cluster should be used.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Developer service.
   */
  @Autowired
  private transient DeveloperService developerService;

  /**
   * Mail content builder.
   */
  @Autowired
  private transient MailContentBuilder mailContentBuilder;

  /**
   * Mail sender.
   */
  @Autowired
  private transient MailSender mailSender;

  /**
   * 发送验证邮件。
   */
  public void sendVerificationEmail(String developerId, String email) {
    LOG.debug("Enter. developerId: {}, email: {}.", developerId, email);

    String verificationCode = RandomStringUtils.randomAlphanumeric(CODE_LENGTH);

    redisTemplate.opsForValue()
      .set(String.format(RedisKeyUtil.VERIFY_KEY_FORMAT, developerId), verificationCode,
        VERIFY_EXPIRE_TIME, TimeUnit.MILLISECONDS);

    String message = mailContentBuilder.getVerifyContent(developerId, verificationCode);

    mailSender.send(email, VERIFY_SUBJECT, message);

    LOG.debug("Exit.");
  }

  /**
   * Resend verify email.
   *
   * @param id
   */
  public void resendVerifyEmail(String id) {
    LOG.debug("Enter. id: {}.", id);

    Developer developer = developerService.get(id);

    if (developer.getStatus().equals(AccountStatus.VERIFIED)) {
      LOG.debug("Developer has bean verified, do not need to verify again.");
      return;
    }

    sendVerificationEmail(id, developer.getEmail());

    LOG.debug("Exit.");
  }

  /**
   * Send reset password link to developer's email.
   *
   * @param email
   */
  public void sendResetToken(String email) {
    LOG.debug("Enter. email: {}.", email);

    Developer developer = developerService.getWithEmail(email);

    String resetToken = RandomStringUtils.randomAlphanumeric(CODE_LENGTH);

    redisTemplate.opsForValue().
        set(String.format(RedisKeyUtil.RESET_KEY_FORMAT, developer.getId()), resetToken,
            RESET_EXPIRE_TIME, TimeUnit.MILLISECONDS);

    String message = mailContentBuilder.getResetContent(developer.getId(), resetToken);

    mailSender.send(email, RESET_SUBJECT, message);
  }

  /**
   * Verify email
   *
   * @param developerId
   * @param code
   */
  public void verifyEmail(String developerId, String code) {
    LOG.debug("Enter. developerId: {}, verificationCode: {}.", developerId, code);

    String key = String.format(RedisKeyUtil.VERIFY_KEY_FORMAT, developerId);
    String requestCode = redisTemplate.opsForValue().get(key).toString();

    if (!code.equals(requestCode)) {
      LOG.debug("VerificationCode is not match");
      throw new ParametersException("VerificationCode is not match");
    }

    Developer developer = developerService.get(developerId);
    developer.setStatus(AccountStatus.VERIFIED);
    developerService.save(developer);

    redisTemplate.delete(key);
  }
}
