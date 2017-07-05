package com.umasuo.developer.application.dto.action;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Davis on 17/7/3.
 */
@Data
public class ResetPassword{

  @NotNull
  private String developerId;

  private static final long serialVersionUID = 5309491764291388935L;
  @NotNull
  private String token;

  @NotNull
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  private String newPassword;
}
