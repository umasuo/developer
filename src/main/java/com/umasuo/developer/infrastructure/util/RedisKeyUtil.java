package com.umasuo.developer.infrastructure.util;

/**
 * Redis key sets.
 */
public class RedisKeyUtil {

  /**
   * Verify key format.
   */
  public static final String VERIFY_KEY_FORMAT = "developer:%s:verify";

  /**
   * Reset key format.
   */
  public static final String RESET_KEY_FORMAT = "developer:%s:reset";

  /**
   * Developer key format.
   */
  public static final String DEVELOPER_KEY_FORMAT = "developer:%s";

  /**
   * the key in mapper which in cache.
   */
  public final static String DEVELOPER_SESSION_KEY = "session";
}
