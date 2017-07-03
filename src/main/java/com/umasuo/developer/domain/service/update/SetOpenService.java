package com.umasuo.developer.domain.service.update;

import com.umasuo.developer.application.dto.action.SetOpenStatus;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/7/3.
 */
@Service(UpdateActionUtils.SET_OPEN_STATUS)
public class SetOpenService implements Updater<Developer, UpdateAction>{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SetOpenService.class);


  @Override
  public void handle(Developer developer, UpdateAction updateAction) {
    SetOpenStatus action = (SetOpenStatus) updateAction;
    boolean openStatus = action.getOpenable();

    developer.setOpenable(openStatus);
  }
}
