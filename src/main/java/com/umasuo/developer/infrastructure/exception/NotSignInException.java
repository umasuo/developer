package com.umasuo.developer.infrastructure.exception;

/**
 * Not sign in exception.
 */
public class NotSignInException extends RuntimeException {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 359351268133413508L;

  /**
   * Default constructor.
   */
  public NotSignInException() {
    super();
  }

  /**
   * Constructor with message.
   *
   * @param msg
   */
  public NotSignInException(String msg) {
    super(msg);
  }

  /**
   * Constructor with message and cause.
   *
   * @param msg
   * @param e
   */
  public NotSignInException(String msg, Throwable e) {
    super(msg, e);
  }
}
