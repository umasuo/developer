package com.umasuo.developer.application.dto.action;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Updater for reset password.
 */
@Data
public class ResetPassword implements Serializable {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 5309491764291388935L;

  /**
   * Developer Id.
   */
  @NotNull
  private String developerId;


  /**
   * Reset token.
   */
  @NotNull
  private String token;

  /**
   * New password.
   */
  @NotNull
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  private String newPassword;
}
