package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.service.VerificationApplication;
import com.umasuo.exception.AuthFailedException;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static com.umasuo.developer.infrastructure.Router.DEVELOPER_VERIFY;
import static com.umasuo.developer.infrastructure.Router.ID;

/**
 * Verify controller.
 */
@CrossOrigin
@RestController
public class VerifyController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(VerifyController.class);

  /**
   * Verify application.
   */
  @Autowired
  private transient VerificationApplication verificationApplication;

  /**
   * verify email.
   *
   * @param developerId
   * @param code
   * @return
   */
  @GetMapping(value = DEVELOPER_VERIFY, params = "code")
  public ModelAndView verifyEmail(@PathVariable(ID) String developerId,
                                  @RequestParam("code") String code) {
    LOGGER.info("Enter. developerId: {}, token: {}.", developerId, code);

    // TODO: 17/7/6 跳转到失败页面
    ModelAndView redirectView = new ModelAndView("redirect:http://www.google.com");

    try {
      verificationApplication.verifyEmail(developerId, code);
      // TODO: 17/7/6 跳转到成功页面
      redirectView = new ModelAndView("redirect:http://www.baidu.com");

    } catch (NotExistException ex) {
      LOGGER.debug("Developer not exist.");
      // TODO: 17/7/6 开发者不存在
      redirectView = new ModelAndView("redirect:http://www.jd.com");
    } catch (ParametersException pEx) {
      LOGGER.debug("Verify code not match.");
      // TODO: 17/7/6 验证不通过，验证码过期或者不对
      redirectView = new ModelAndView("redirect:http://www.google.com");
    }

    LOGGER.info("Exit.");

    return redirectView;
  }

  /**
   * send verify email.
   *
   * @param id
   * @param developerId
   */
  @PostMapping(value = DEVELOPER_VERIFY)
  public void sendVerifyEmail(@PathVariable(ID) String id,
                              @RequestHeader("developerId") String developerId) {
    LOGGER.info("Enter. id: {}, developerId: {}.", id, developerId);

    // TODO: 17/7/4 最好移到一个统一的地方
    if (!id.equals(developerId)) {
      LOGGER.debug("Developer: {} Can not update other developer: {}.", developerId, id);
      throw new AuthFailedException("Developer do not have auth to update other developer");
    }

    verificationApplication.resendVerifyEmail(id);

    LOGGER.info("Exit.");
  }
}
