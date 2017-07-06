package com.umasuo.developer.application.dto.mapper;

import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Davis on 17/7/3.
 */
public final class MailMessageMapper {

  /**
   * Instantiates a new Mail message mapper.
   */
  private MailMessageMapper() {
  }

  /**
   * Build simple mail message.
   *
   * @param email the email
   * @param subject the subject
   * @param message the message
   * @return the simple mail message
   */
  public static SimpleMailMessage build(String email, String subject, String message) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(email);
    mailMessage.setSubject(subject);
    mailMessage.setText(message);
    return mailMessage;
  }

  public static String createMessage(String developerId, String verificationCode) {
    // TODO: 17/7/3
    String msg =
        "http://localhost:8804/v1/developers/" + developerId + "/verify?code=" + verificationCode;
    return msg;
  }
}
