package com.umasuo.developer.application.dto;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * Created by umasuo on 17/3/7.
 */
@Data
public class DeveloperView {

  private String id;

  /**
   * The Created at.
   */
  protected ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  protected ZonedDateTime lastModifiedAt;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * email of the developer,
   */
  private String email;

  /**
   * phone number.
   */
  private String phone;

  /**
   * developer status: unverified, verified, disabled.
   */
  private short status;
}
