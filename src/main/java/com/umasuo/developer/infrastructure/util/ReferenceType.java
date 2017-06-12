package com.umasuo.developer.infrastructure.util;

/**
 * Created by Davis on 17/6/12.
 */
public enum ReferenceType {
  DATA_DEFINITION("data-definition");

  /**
   * Enum value.
   */
  private String value;

  /**
   * Private constructor.
   *
   * @param value enum value
   */
  private ReferenceType(String value) {
    this.value = value;
  }

  /**
   * Get type.
   *
   * @return String
   */
  public String getType() {
    return this.value;
  }
}
