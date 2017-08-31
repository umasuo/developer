package com.umasuo.developer.application.dto.action;

import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.update.UpdateActionUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Updater for set open status.
 */
@Setter
@Getter
@ToString
public class SetOpenStatus implements UpdateAction {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = -2773766503211755706L;

  /**
   * The Openable.
   */
  @NotNull
  private Boolean openable;

  /**
   * Get action name.
   *
   * @return
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.SET_OPEN_STATUS;
  }
}
