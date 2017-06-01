package com.umasuo.developer.application.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户权限状态，包含：是否登陆，所拥有的权限（scope）
 * Created by umasuo on 17/6/1.
 */
@Data
public class AuthStatus {

  private String developerId;

  private boolean isLogin;

  //TODO 后期添加scope等控制
//  private List<String> scopes;
}
