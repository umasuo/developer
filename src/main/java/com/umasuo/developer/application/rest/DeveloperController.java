package com.umasuo.developer.application.rest;

import static com.umasuo.developer.infrastructure.Router.DEVELOPER_ROOT;
import static com.umasuo.developer.infrastructure.Router.DEVELOPER_WITH_ID;
import static com.umasuo.developer.infrastructure.Router.ID;

import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.application.dto.action.OpenStatusRequest;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Developer controller.
 *
 * Created by Davis on 17/6/3.
 */
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

  /**
   * Check if developer exist.
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
  @GetMapping(DEVELOPER_ROOT)
  public List<DeveloperView> getOpenDeveloper() {
    LOG.info("Enter.");

    List<DeveloperView> result = developerService.getOpenDeveloper();

    LOG.info("Exit. open developer size: {}.", result.size());

    return result;
  }

  /**
   * Update developer open status.
   *
   * @param id developer id
   * @param request update request
   * @return updated developer
   */
  @PutMapping(DEVELOPER_WITH_ID)
  public DeveloperView updateOpenStatus(@PathVariable(ID) String id,
      @RequestBody OpenStatusRequest request) {
    LOG.info("Enter. developerId: {}, openStatusRequest: {}.", id, request);

    DeveloperView result = developerService
        .updateOpenStatus(id, request.getVersion(), request.getOpenable());

    LOG.info("Exit.");

    return result;
  }
}
