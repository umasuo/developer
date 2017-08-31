package com.umasuo.developer.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Developer session.
 */
@Data
public class DeveloperSession implements Serializable {
  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 3640903092407994272L;

  /**
   * Developer view.
   */
  private DeveloperView developerView;

  /**
   * Token.
   */
  private String token;

  /**
   * last active time.
   */
  private long lastActiveTime;

  /**
   * Should move it to config.
   */
  public static final long EXPIRE_IN = 2 * 60 * 60 * 1000L;//2 hour

  /**
   * constructor with parameters.
   *
   * @param developerView
   * @param token
   */
  public DeveloperSession(DeveloperView developerView, String token) {
    this.developerView = developerView;
    this.token = token;
    lastActiveTime = System.currentTimeMillis();
  }

  /**
   * Getter.
   *
   * @return
   */
  public DeveloperView getDeveloperView() {
    return developerView;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setDeveloperView(DeveloperView developerView) {
    this.developerView = developerView;
  }

  /**
   * Getter.
   *
   * @return
   */
  public String getToken() {
    return token;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Getter.
   *
   * @return
   */
  public long getLastActiveTime() {
    return lastActiveTime;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setLastActiveTime(long lastActiveTime) {
    this.lastActiveTime = lastActiveTime;
  }

}
