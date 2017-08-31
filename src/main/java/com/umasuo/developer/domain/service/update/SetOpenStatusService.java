package com.umasuo.developer.domain.service.update;

import com.umasuo.developer.application.dto.action.SetOpenStatus;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;
import org.springframework.stereotype.Service;

/**
 * Updater for set open status.
 */
@Service(UpdateActionUtils.SET_OPEN_STATUS)
public class SetOpenStatusService implements Updater<Developer, UpdateAction> {

  /**
   * Update open status.
   *
   * @param developer
   * @param updateAction
   */
  @Override
  public void handle(Developer developer, UpdateAction updateAction) {
    SetOpenStatus action = (SetOpenStatus) updateAction;
    boolean openStatus = action.getOpenable();

    developer.setOpenable(openStatus);
  }
}
