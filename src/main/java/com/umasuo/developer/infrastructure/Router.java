package com.umasuo.developer.infrastructure;

/**
 * Created by umasuo on 17/3/7.
 */
public class Router {
  /**
   * authentication root.
   */
  public static final String DEVELOPER_ROOT = "/v1/developers";

  /**
   * login.
   */
  public static final String DEVELOPER_SIGN_IN = DEVELOPER_ROOT + "/signin";

  /**
   * logout.
   */
  public static final String DEVELOPER_SIGN_OUT = DEVELOPER_ROOT + "/signout";

  /**
   * sign up.
   */
  public static final String DEVELOPER_SIGN_UP = DEVELOPER_ROOT + "/signup";

  /**
   * Id.
   */
  public static final String ID = "id";

  /**
   * Developer with id.
   */
  public static final String DEVELOPER_WITH_ID = DEVELOPER_ROOT + "/{" + ID + "}";

  /**
   * login.
   */
  public static final String DEVELOPER_SIGN_IN_STATUS = DEVELOPER_WITH_ID + "/status";


}
