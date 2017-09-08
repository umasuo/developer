package com.umasuo.developer.application.dto.mapper;

import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;

/**
 * Mail message mapper.
 */
public final class MailMessageMapper {

  /**
   * Send email.
   */
  private static final String ACCOUNT_NAME = "public@evacloud.cn";

  /**
   * Send name.
   */
  private static final String FROM_ALIAS = "伊娃云官方";

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
  public static SingleSendMailRequest build(String email, String subject, String message) {
    SingleSendMailRequest request = new SingleSendMailRequest();

    request.setAccountName(ACCOUNT_NAME);
    request.setFromAlias(FROM_ALIAS);
    request.setAddressType(1);
    request.setReplyToAddress(true);
    request.setToAddress(email);
    request.setSubject(subject);
    request.setHtmlBody(message);

    return request;
  }
}
