package com.umasuo.developer.application.rest;

import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.application.service.DeveloperApplication;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.domain.service.DeveloperService;
import com.umasuo.developer.infrastructure.Router;
import com.umasuo.developer.infrastructure.update.UpdateRequest;
import com.umasuo.exception.AuthFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 */
@CrossOrigin
@RestController
public class DeveloperController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperController.class);

  /**
   * The DeveloperService.
   */
  @Autowired
  private transient DeveloperService developerService;

  /**
   * Developer application.
   */
  @Autowired
  private transient DeveloperApplication developerApplication;

  /**
   * Check if developer exist.
   * 内部接口，API Gateway不配置外部访问的路劲。
   *
   * @param id the developer id
   * @return true if developer exist and false if not
   */
  @GetMapping(DEVELOPER_WITH_ID)
  public boolean checkDeveloper(@PathVariable(ID) String id) {
    LOGGER.info("Enter. developerId: {}.", id);

    boolean result = true;
    Developer developer = developerService.get(id);
    if (developer == null) {
      LOGGER.debug("Developer: {} not exist.", id);
      result = false;
    }

    LOGGER.info("Exit. developer: {} exist? {}.", id, result);

    return result;
  }

  /**
   * Get open developer.
   *
   * @return list of DeveloperView
   */
  @GetMapping(value = DEVELOPER_ROOT, params = "open")
  public List<DeveloperView> getOpenDeveloper(@RequestParam boolean open) {
    LOGGER.info("Enter.");

    List<DeveloperView> result = developerService.getOpenDeveloper();

    LOGGER.info("Exit. open developer size: {}.", result.size());

    return result;
  }

  /**
   * Update.
   *
   * @param id
   * @param developerId
   * @param request
   * @return
   */
  @PutMapping(value = DEVELOPER_WITH_ID)
  public DeveloperView update(@PathVariable(ID) String id,
                              @RequestHeader("developerId") String developerId,
                              @RequestBody UpdateRequest request) {
    LOGGER.info("Enter. id: {}, developer: {}.");
    if (!id.equals(developerId)) {
      LOGGER.debug("Developer: {} Can not update other developer: {}.", developerId, id);
      throw new AuthFailedException("Developer do not have auth to update other developer");
    }

    DeveloperView updatedDeveloper = developerService
      .update(id, request.getVersion(), request.getActions());

    LOGGER.debug("Exit.");
    return updatedDeveloper;
  }

  /**
   * Count developers
   *
   * @return
   */
  @GetMapping("/v1/admin/developers/count")
  public Long countDevelopers() {
    LOGGER.info("Enter.");

    Long count = developerService.countDevelopers();

    LOGGER.info("Exit. developer count: {}.", count);

    return count;
  }

  /**
   * Get all developers.
   *
   * @return
   */
  @GetMapping(Router.DEVELOPER_GET_ALL)
  public List<DeveloperView> getAllDevelopers() {
    LOGGER.info("Enter.");

    List<DeveloperView> developers = developerApplication.getAllDevelopers();

    LOGGER.debug("Exit. developer size: {}.", developers.size());

    return developers;
  }
}
