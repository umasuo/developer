package com.umasuo.developer.infrastructure.validator;

import com.umasuo.exception.ConflictException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Version validator.
 * Created by Davis on 17/5/27.
 */
public final class VersionValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(VersionValidator.class);

  /**
   * Validate.
   *
   * @param entityVersion the entity version
   * @param version the version
   */
  public static void validate(Integer entityVersion, Integer version) {
    if (! version.equals(entityVersion)) {
      LOG.debug("Version not match, request version: {}, entity version: {}.",
          version, entityVersion);
      throw new ConflictException("Version not match");
    }
  }
}
