package com.umasuo.developer.application.dto.action;

import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.update.UpdateActionUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/6/8.
 */
@Setter
@Getter
@ToString
public class SetOpenStatus implements UpdateAction{

  /**
   * The Openable.
   */
  @NotNull
  private Boolean openable;

  @Override
  public String getActionName() {
    return UpdateActionUtils.SET_OPEN_STATUS;
  }
}
