package com.umasuo.developer.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Davis on 17/6/8.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"type", "id"})
public class Reference {

  /**
   * The Type.
   */
  private String type;

  /**
   * The Id.
   */
  private String id;
}
