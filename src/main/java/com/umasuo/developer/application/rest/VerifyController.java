package com.umasuo.developer.application.rest;

import static com.umasuo.developer.infrastructure.Router.DEVELOPER_VERIFY;
import static com.umasuo.developer.infrastructure.Router.DEVELOPER_WITH_ID;
import static com.umasuo.developer.infrastructure.Router.ID;

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
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by Davis on 17/7/6.
 */
@CrossOrigin
@RestController
public class VerifyController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(VerifyController.class);

  @Autowired
  private transient VerificationApplication verificationApplication;

  @GetMapping(value = DEVELOPER_VERIFY, params = "code")
  public ModelAndView verifyEmail(@PathVariable(ID) String developerId,
      @RequestParam("code") String code) {
    LOG.info("Enter. developerId: {}, token: {}.", developerId, code);

    // TODO: 17/7/6 跳转到失败页面
    ModelAndView redirectView = new ModelAndView("redirect:http://www.google.com");

    try {
      verificationApplication.verifyEmail(developerId, code);
      // TODO: 17/7/6 跳转到成功页面
      redirectView = new ModelAndView("redirect:http://www.baidu.com");

    } catch (NotExistException ex) {
      LOG.debug("Developer not exist.");
      // TODO: 17/7/6 开发者不存在
      redirectView = new ModelAndView("redirect:http://www.jd.com");
    } catch (ParametersException pEx) {
      LOG.debug("Verify code not match.");
      // TODO: 17/7/6 验证不通过，验证码过期或者不对
      redirectView = new ModelAndView("redirect:http://www.google.com");
    }

    LOG.info("Exit.");

    return redirectView;
  }

  @PostMapping(value = DEVELOPER_VERIFY)
  public void sendVerifyEmail(@PathVariable(ID) String id,
      @RequestHeader("developerId") String developerId) {
    LOG.info("Enter. id: {}, developerId: {}.", id, developerId);

    // TODO: 17/7/4 最好移到一个统一的地方
    if (!id.equals(developerId)) {
      LOG.debug("Developer: {} Can not update other developer: {}.", developerId, id);
      throw new AuthFailedException("Developer do not have auth to update other developer");
    }

    verificationApplication.resendVerifyEmail(id);

    LOG.info("Exit.");
  }
}
