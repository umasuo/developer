package com.umasuo.developer.application.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignInResult implements Serializable {

  /**
   * customer for view.
   */
  private DeveloperView developerView;

  /**
   * String token.
   */
  private String token;

  public SignInResult(DeveloperView developerView, String token) {
    this.developerView = developerView;
    this.token = token;
  }
}
