package com.umasuo.developer.domain.exception;

/**
 * Created by umasuo on 17/3/7.
 */
public class NotLoginException extends RuntimeException {

  public NotLoginException() {
    super();
  }

  public NotLoginException(String msg) {
    super(msg);
  }

  public NotLoginException(String msg, Throwable e) {
    super(msg, e);
  }
}
