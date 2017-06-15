package com.umasuo.developer.application.dto;

import com.umasuo.developer.infrastructure.enums.AccountStatus;
import lombok.Data;

/**
 * Created by umasuo on 17/3/7.
 */
@Data
public class DeveloperView {

  private String id;

  /**
   * The Created at.
   */
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  protected Long lastModifiedAt;

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
  private AccountStatus status;

  /**
   * The Openable.
   */
  private Boolean openable;
}
