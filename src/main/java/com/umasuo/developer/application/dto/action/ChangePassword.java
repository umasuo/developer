package com.umasuo.developer.application.dto.action;

import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.update.UpdateActionUtils;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Updater for change password.
 */
@Data
public class ChangePassword implements UpdateAction {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 4824117085152571457L;

  /**
   * old password.
   */
  @NotNull
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  private String oldPassword;

  /**
   * new password.
   */
  @NotNull
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  private String newPassword;

  /**
   * Get Action name.
   *
   * @return
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.CHANGE_PASSWORD;
  }
}
