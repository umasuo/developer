package com.umasuo.developer.infrastructure.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.umasuo.developer.application.dto.action.ChangePassword;
import com.umasuo.developer.application.dto.action.ResetPassword;
import com.umasuo.developer.application.dto.action.SetOpenStatus;

import java.io.Serializable;

/**
 * Created by Davis on 17/7/3.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property =
    "action")
@JsonSubTypes( {
    @JsonSubTypes.Type(value = SetOpenStatus.class, name = UpdateActionUtils.SET_OPEN_STATUS),
    @JsonSubTypes.Type(value = ChangePassword.class, name = UpdateActionUtils.CHANGE_PASSWORD),
    @JsonSubTypes.Type(value = ResetPassword.class, name = UpdateActionUtils.RESET_PASSWORD)
})
public interface UpdateAction extends Serializable {

  String getActionName();
}
