package com.umasuo.developer.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Davis on 17/6/9.
 */
@Getter
@Setter
@ToString
public class ResourcePermissionView {

  /**
   * The id.
   */
  private String id;

  /**
   * The Applicant id.
   */
  private String applicantId;

  /**
   * The Acceptor id.
   */
  private String acceptorId;

  /**
   * The Device id.
   */
  private String deviceId;

  /**
   * The References.
   */
  private List<Reference> references;
}
