package com.umasuo.developer.application.dto.action;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/6/8.
 */
@Setter
@Getter
@ToString
public class OpenStatusRequest {

  /**
   * The Openable.
   */
  @NotNull
  private Boolean openable;

  /**
   * The Version.
   */
  @NotNull
  @Min(1)
  private Integer version;
}
