package com.umasuo.developer.domain.exception;

/**
 * Created by umasuo on 17/3/7.
 */
public class NotSignInException extends RuntimeException {

  public NotSignInException() {
    super();
  }

  public NotSignInException(String msg) {
    super(msg);
  }

  public NotSignInException(String msg, Throwable e) {
    super(msg, e);
  }
}
