package com.umasuo.developer.domain.service;

import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.application.dto.mapper.DeveloperMapper;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.infrastructure.enums.AccountStatus;
import com.umasuo.developer.infrastructure.repository.DeveloperRepository;
import com.umasuo.developer.infrastructure.update.DeveloperUpdaterService;
import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.util.PasswordUtil;
import com.umasuo.developer.infrastructure.validator.VersionValidator;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Developer service.
 */
@Service
public class DeveloperService {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DeveloperService.class);

  /**
   * repository.
   * for this, we should consider to change our database.
   */
  @Autowired
  private transient DeveloperRepository repository;

  /**
   * Developer Update service.
   */
  @Autowired
  private transient DeveloperUpdaterService updaterService;

  /**
   * create a developer from an sample.
   *
   * @param developer the sample
   * @return result. developer
   */
  public Developer save(Developer developer) {
    LOGGER.debug("CreateDeveloper: {}", developer);
    return this.repository.save(developer);
  }

  /**
   * create a developer with email & password.
   *
   * @param email    email
   * @param password raw password.
   * @return created developer
   */
  public Developer create(String email, String password) {
    LOGGER.debug("email:{}", email);

    Developer developer = this.repository.findOneByEmail(email);

    if (developer != null) {
      throw new AlreadyExistException("Developer already existing.");
    }

    developer = new Developer();

    developer.setEmail(email);
    developer.setStatus(AccountStatus.UNVERIFIED);
    developer.setOpenable(false);//默认不公开任何数据

    String encryptedPwd = PasswordUtil.hashPassword(password);
    developer.setPassword(encryptedPwd);

    return save(developer);
  }

  /**
   * Get one developer by it's id.
   *
   * @param id developer id.
   * @return existing developer.
   */
  public Developer get(String id) {
    LOGGER.debug("Enter. developerId: {}.", id);

    Developer developer = repository.findOne(id);

    if (developer == null) {
      LOGGER.debug("Can not find developer: {}.", id);
      throw new NotExistException("Developer not exist");
    }

    LOGGER.debug("Exit.");

    return developer;
  }

  /**
   * Check if developer exist.
   *
   * @param id the developerId
   * @return boolean
   */
  public boolean exists(String id) {
    LOGGER.debug("Enter. developerId: {}.", id);

    boolean result = repository.exists(id);

    LOGGER.debug("Exit. developer: {} exist? {}", id, result);

    return result;
  }

  /**
   * getAllForApplicant one developer with it's email.
   *
   * @param email the email
   * @return with email
   */
  public Developer getWithEmail(String email) {
    LOGGER.debug("GetDeveloper: email: {}", email);
    Developer developer = this.repository.findOneByEmail(email);
    if (developer == null) {
      throw new NotExistException("Developer not exist.");
    }
    return developer;
  }

  /**
   * Get open developers.
   *
   * @return list of DeveloperView
   */
  public List<DeveloperView> getOpenDeveloper() {
    LOGGER.debug("Enter.");

    List<Developer> openDevelopers = repository.findByOpenable(true);

    List<DeveloperView> result = DeveloperMapper.toView(openDevelopers);

    LOGGER.debug("Exit. open developer size: {}.", result.size());

    return result;
  }

  /**
   * Update developer.
   *
   * @param id
   * @param version
   * @param actions
   * @return
   */
  public DeveloperView update(String id, Integer version, List<UpdateAction> actions) {
    Developer developer = repository.findOne(id);
    if (developer == null) {
      LOGGER.debug("Developer: {} not exist.", id);
      throw new NotExistException("Developer not exist");
    }

    VersionValidator.validate(version, developer.getVersion());

    actions.stream().forEach(action -> updaterService.handle(developer, action));

    repository.save(developer);

    DeveloperView updatedDeveloper = DeveloperMapper.toView(developer);

    return updatedDeveloper;
  }

  /**
   * Count developers.
   *
   * @return
   */
  public Long countDevelopers() {
    LOGGER.debug("Enter.");

    long count = repository.count();

    LOGGER.debug("Exit. developer count: {}.", count);

    return count;
  }

  /**
   * Get all developers.
   *
   * @return
   */
  public List<Developer> getAllDevelopers() {
    LOGGER.debug("Enter.");

    List<Developer> developers = repository.findAll();

    LOGGER.debug("Exit. developer size: {}.", developers.size());

    return developers;
  }
}
