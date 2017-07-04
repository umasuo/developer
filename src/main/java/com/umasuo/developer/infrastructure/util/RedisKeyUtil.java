package com.umasuo.developer.infrastructure.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Davis on 17/7/4.
 */
public class RedisKeyUtil {

  public static final String VERIFY_KEY_FORMAT = "developer:%s:verify";

  public static final String RESET_KEY_FORMAT = "developer:%s:reset";

  public static final String DEVELOPER_KEY_FORMAT = "developer:%s";

  /**
   * the key in mapper which in cache.
   */
  public final static String SIGN_IN_CACHE_KEY = "token";
}
