package com.umasuo.developer.infrastructure;

/**
 * Created by umasuo on 17/3/7.
 */
public class Router {
  /**
   * authentication root.
   */
  public static final String DEVELOPER_ROOT = "/developers";


  /**
   * login.
   */
  public static final String DEVELOPER_SIGN_IN = DEVELOPER_ROOT + "/signin";

  /**
   * login.
   */
  public static final String DEVELOPER_SIGN_IN_STATUS = DEVELOPER_ROOT + "/signin/status";

  /**
   * logout.
   */
  public static final String DEVELOPER_SIGN_OUT = DEVELOPER_ROOT + "/signout";

  /**
   * sign up.
   */
  public static final String DEVELOPER_SIGN_UP = DEVELOPER_ROOT + "/signup";

}
