package com.umasuo.developer.application.dto;


import java.io.Serializable;

/**
 * Sign in result.
 */
public class SignInResult implements Serializable {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = -5118543908111419503L;

  /**
   * customer for view.
   */
  private DeveloperView developerView;

  /**
   * String token.
   */
  private String token;

  /**
   * Constructor.
   *
   * @param developerView
   * @param token
   */
  public SignInResult(DeveloperView developerView, String token) {
    this.developerView = developerView;
    this.token = token;
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
}
