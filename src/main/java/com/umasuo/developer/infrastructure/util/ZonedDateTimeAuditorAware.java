package com.umasuo.developer.infrastructure.util;

import org.springframework.data.domain.AuditorAware;

/**
 * Created by umasuo on 17/6/1.
 */
public class ZonedDateTimeAuditorAware implements AuditorAware<String> {

  /**
   * getAllForApplicant current auditor.
   * @return null.
   */
  @Override
  public String getCurrentAuditor() {
    return null;
  }
}