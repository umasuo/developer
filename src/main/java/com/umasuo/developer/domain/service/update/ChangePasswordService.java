package com.umasuo.developer.domain.service.update;

import com.umasuo.developer.application.dto.action.ChangePassword;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.update.UpdateActionUtils;
import com.umasuo.developer.infrastructure.util.PasswordUtil;
import com.umasuo.exception.PasswordErrorException;
import com.umasuo.model.Updater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Change password service.
 */
@Service(UpdateActionUtils.CHANGE_PASSWORD)
public class ChangePasswordService implements Updater<Developer, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordService.class);

  /**
   * Update developer's password.
   *
   * @param developer
   * @param updateAction
   */
  @Override
  public void handle(Developer developer, UpdateAction updateAction) {
    ChangePassword action = (ChangePassword) updateAction;
    String oldPassword = action.getOldPassword();
    boolean isOldPwdRight = PasswordUtil.checkPassword(oldPassword, developer.getPassword());
    if (!isOldPwdRight) {
      LOGGER.debug("Old password is not correct.");
      throw new PasswordErrorException("Old password is not correct");
    }

    String newPassword = action.getNewPassword();
    developer.setPassword(PasswordUtil.hashPassword(newPassword));
  }
}
