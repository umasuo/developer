package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.application.dto.action.OpenStatusRequest;
import com.umasuo.developer.application.service.VerificationApplication;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.umasuo.developer.infrastructure.Router.DEVELOPER_ROOT;
import static com.umasuo.developer.infrastructure.Router.DEVELOPER_WITH_ID;
import static com.umasuo.developer.infrastructure.Router.ID;

/**
 * Developer controller.
 * <p>
 * Created by Davis on 17/6/3.
 */
@CrossOrigin
@RestController
public class DeveloperController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(DeveloperController.class);

  /**
   * The DeveloperService.
   */
  @Autowired
  private transient DeveloperService developerService;

  @Autowired
  private transient VerificationApplication verificationApplication;

  /**
   * Check if developer exist.
   * 内部接口，API Gateway不配置外部访问的路劲。
   *
   * @param id the developer id
   * @return true if developer exist and false if not
   */
  @GetMapping(DEVELOPER_WITH_ID)
  public boolean checkDeveloper(@PathVariable(ID) String id) {
    LOG.info("Enter. developerId: {}.", id);

    boolean result = true;
    Developer developer = developerService.get(id);
    if (developer == null) {
      LOG.debug("Developer: {} not exist.", id);
      result = false;
    }

    LOG.info("Exit. developer: {} exist? {}.", id, result);

    return result;
  }

  /**
   * Get open developer.
   *
   * @return list of DeveloperView
   */
  @GetMapping(value = DEVELOPER_ROOT, params = "open")
  public List<DeveloperView> getOpenDeveloper(@RequestParam boolean open) {
    LOG.info("Enter.");

    List<DeveloperView> result = developerService.getOpenDeveloper();

    LOG.info("Exit. open developer size: {}.", result.size());

    return result;
  }

  /**
   * Update developer open status.
   *
   * @param developerId developer id
   * @param request     update request
   * @return updated developer
   */
  @PutMapping(value = DEVELOPER_WITH_ID)
  public DeveloperView updateOpenStatus(@PathVariable("id") String id,
                                        @RequestHeader("developerId") String developerId,
                                        @RequestBody OpenStatusRequest request) {
    LOG.info("Enter. developerId: {}, openStatusRequest: {}.", developerId, request);

    DeveloperView result = developerService
        .updateOpenStatus(developerId, request.getVersion(), request.getOpenable());

    LOG.info("Exit. result: {}.", result);

    return result;
  }

  @GetMapping(value = DEVELOPER_WITH_ID, params = "verificationCode")
  public void verifyEmail(@PathVariable(ID) String developerId, @RequestParam("verificationCode") String code) {
    LOG.info("Enter. developerId: {}, token: {}.", developerId, code);

    verificationApplication.verifyEmail(developerId, code);

    LOG.info("Exit.");
  }
}
