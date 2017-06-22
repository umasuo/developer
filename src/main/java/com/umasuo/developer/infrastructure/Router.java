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

  /**
   * Id.
   */
  public static final String ID = "id";

  /**
   * Developer with id.
   */
  public static final String DEVELOPER_WITH_ID = DEVELOPER_ROOT + "/{" + ID + "}";

  /**
   * Open developer root.
   */
  public static final String OPEN_DEVELOPER_ROOT = DEVELOPER_ROOT + "/open";

  /**
   * Resource root.
   */
  public static final String RESOURCE_ROOT = DEVELOPER_ROOT + "/resource";

  /**
   * Permission root.
   */
  public static final String PERMISSION_ROOT = RESOURCE_ROOT + "/permissions";

  /**
   * Applicant permission root.
   */
  public static final String APPLICANT_PERMISSION_ROOT = PERMISSION_ROOT + "/applicant";

  /**
   * Acceptor permission root.
   */
  public static final String ACCEPTOR_PERMISSION_ROOT = PERMISSION_ROOT + "/acceptor";

  /**
   * Permission with id.
   */
  public static final String PERMISSION_WITH_ID = PERMISSION_ROOT + "/{id}";

  /**
   * Request root.
   */
  public static final String REQUEST_ROOT = RESOURCE_ROOT + "/request";

  /**
   * Applicant request root.
   */
  public static final String APPLICANT_REQUEST_ROOT = REQUEST_ROOT + "/applicant";

  /**
   * Acceptor request root.
   */
  public static final String ACCEPTOR_REQUEST_ROOT = REQUEST_ROOT + "/acceptor";

  /**
   * Acceptor request with id.
   */
  public static final String ACCEPTOR_REQUEST_WITH_ID = ACCEPTOR_REQUEST_ROOT + "/{id}";

  /**
   * User request root.
   */
  public static final String USER_REQUEST_ROOT = REQUEST_ROOT + "/user";
}
