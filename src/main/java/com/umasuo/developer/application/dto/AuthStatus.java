package com.umasuo.developer.application.dto;

import lombok.Data;

/**
 * Developer sign in status and it's scopes.
 */
@Data
public class AuthStatus {

  /**
   * Developer id.
   */
  private String developerId;

  /**
   * Is login.
   */
  private boolean isLogin;

  //TODO 后期添加scope等控制
//  private List<String> scopes;
}
