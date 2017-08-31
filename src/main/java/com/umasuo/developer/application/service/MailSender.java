package com.umasuo.developer.application.service;

import com.umasuo.developer.application.dto.mapper.MailMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Mail sender.
 */
@Service(value = "developerMailSender")
public class MailSender {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);

  /**
   * Java mail sender.
   */
  @Autowired
  private transient JavaMailSender javaMailSender;

  /**
   * Send an email.
   *
   * @param email
   * @param subject
   * @param message
   */
  @Async
  public void send(String email, String subject, String message) {
    LOGGER.debug("Enter. email: subject: {}.", email, subject);
    SimpleMailMessage mailMessage = MailMessageMapper.build(email, subject, message);

    javaMailSender.send(mailMessage);

    LOGGER.debug("Exit.");
  }
}
