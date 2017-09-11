package com.umasuo.developer.application.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Sign up model.
 */
public class SignIn {

  /**
   * email.
   */
  @Email(message = "Not a valid email")
  @NotNull(message = "Email can not be null")
  private String email;

  /**
   * ^                 # start-of-string
   * (?=.*[0-9])       # a digit must occur at least once
   * (?=.*[a-z])       # a lower case letter must occur at least once
   * (?=\S+$)          # no whitespace allowed in the entire string
   * .{8,}             # anything, at least eight places though
   * $                 # end-of-string
   */
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$", message = "Not a valid password")
  @NotNull(message = "Password can not be null")
  private String password;

  /**
   * To string.
   */
  @Override
  public String toString() {
    return "SignIn{"
        + "email='" + email + '\''
        + '}';
  }

  /**
   * Getter.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Setter.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Getter.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Setter.
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
