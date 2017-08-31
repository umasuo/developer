package com.umasuo.developer.infrastructure;

/**
 * Developer router.
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

  /**
   * logout.
   */
  public static final String DEVELOPER_SIGN_OUT = DEVELOPER_WITH_ID + "/signout";

  /**
   * verify developer after sign up.
   */
  public static final String DEVELOPER_VERIFY = DEVELOPER_WITH_ID + "/verify";

  /**
   * Reset developer's password.
   */
  public static final String DEVELOPER_RESET_PASSWORD = DEVELOPER_ROOT + "/reset-password";

  /**
   * Get all Developers.
   */
  public static final String DEVELOPER_GET_ALL = "/v1/admin/developers";

  /**
   * Count developers.
   */
  public static final String DEVELOPER_COUNT_ALL = "/v1/admin/developers/count";
}
