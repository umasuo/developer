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
   * @param entityVersion the entity version
   * @param version       the version
   */
  public static void validate(Integer entityVersion, Integer version) {
    if (!version.equals(entityVersion)) {
      LOGGER.debug("Version not match, request version: {}, entity version: {}.",
        version, entityVersion);
      throw new ConflictException("Version not match");
    }
  }
}
