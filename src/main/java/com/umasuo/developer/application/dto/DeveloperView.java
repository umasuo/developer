package com.umasuo.developer.application.dto;

import com.umasuo.developer.infrastructure.enums.AccountStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * Developer view.
 */
@Data
public class DeveloperView implements Serializable {

  /**
   * Auto generated serial version uid.
   */
  private static final long serialVersionUID = 3911397336752429079L;
  /**
   * developer id.
   */
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
