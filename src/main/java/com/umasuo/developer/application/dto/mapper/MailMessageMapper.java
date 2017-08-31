package com.umasuo.developer.application.dto.mapper;

import org.springframework.mail.SimpleMailMessage;

/**
 * Mail message mapper.
 */
public final class MailMessageMapper {

  /**
   * Private default constructor.
   */
  private MailMessageMapper() {
  }

  /**
   * Build simple mail message.
   *
   * @param email   the email
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

  /**
   * Create verify message.
   *
   * @param developerId
   * @param verificationCode
   * @return
   */
  public static String createVerifyMessage(String developerId, String verificationCode) {
    // TODO: 17/7/3
    String msg =
      "http://localhost:8804/v1/developers/" + developerId + "/verify?code=" + verificationCode;
    return msg;
  }

  /**
   * Create reset message.
   *
   * @param developerId
   * @param resetToken
   * @return
   */
  public static String createResetMessage(String developerId, String resetToken) {
    // TODO: 17/7/10
    return String.format("developerId: %s, token: %s", developerId, resetToken);
  }
}
