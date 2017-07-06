package com.umasuo.developer.application.dto.action;

import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Davis on 17/7/3.
 */
@Data
public class ChangePassword implements UpdateAction {

  private static final long serialVersionUID = 4824117085152571457L;

  @NotNull
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  private String oldPassword;

  @NotNull
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  private String newPassword;

  @Override
  public String getActionName() {
    return UpdateActionUtils.CHANGE_PASSWORD;
  }
}
