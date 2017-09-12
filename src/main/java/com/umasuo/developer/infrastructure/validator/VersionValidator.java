package com.umasuo.developer.infrastructure.validator;

import com.umasuo.exception.ConflictException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Version validator.
 */
public final class VersionValidator {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(VersionValidator.class);

  /**
   * Private default constructor.
   */
  private VersionValidator() {
  }

  /**
   * Validate.
   *
   * @param requestVersion the request version
   * @param realVersion the real version
   */
  public static void validate(Integer requestVersion, Integer realVersion) {
    if (!realVersion.equals(requestVersion)) {
      LOGGER.debug("Version not match, request version: {}, real version: {}.",
          requestVersion, realVersion);
      throw new ConflictException("Version not match");
    }
  }
}
