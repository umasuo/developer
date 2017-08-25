package com.umasuo.developer.domain.service;

import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.application.dto.mapper.DeveloperMapper;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.infrastructure.enums.AccountStatus;
import com.umasuo.developer.infrastructure.repository.DeveloperRepository;
import com.umasuo.developer.infrastructure.update.DeveloperUpdaterService;
import com.umasuo.developer.infrastructure.update.UpdateAction;
import com.umasuo.developer.infrastructure.util.PasswordUtil;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.ConflictException;
import com.umasuo.exception.NotExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/3/6.
 */
@Service
public class DeveloperService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(DeveloperService.class);

  /**
   * repository.
   * for this, we should consider to change our database.
   */
  @Autowired
  private transient DeveloperRepository repository;

  @Autowired
  private transient DeveloperUpdaterService updaterService;

  /**
   * create a developer from an sample.
   *
   * @param developer the sample
   * @return result. developer
   */
  public Developer save(Developer developer) {
    logger.debug("CreateDeveloper: {}", developer);
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
    logger.debug("CreateDeveloper: email:{}", email);
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
   * getAllForApplicant one developer by it's id.
   *
   * @param id developer id.
   * @return existing developer.
   */
  public Developer get(String id) {
    logger.debug("GetDeveloper: id: {}", id);
    return this.repository.findOne(id);
  }

  /**
   * getAllForApplicant one developer with it's email.
   *
   * @param email the email
   * @return with email
   */
  public Developer getWithEmail(String email) {
    logger.debug("GetDeveloper: email: {}", email);
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
    logger.debug("Enter.");

    List<Developer> openDevelopers = repository.findByOpenable(true);

    List<DeveloperView> result = DeveloperMapper.toModel(openDevelopers);

    logger.debug("Exit. open developer size: {}.", result.size());

    return result;
  }

  public DeveloperView update(String id, Integer version, List<UpdateAction> actions) {
    Developer developer = repository.findOne(id);
    if (developer == null) {
      logger.debug("Developer: {} not exist.", id);
      throw new NotExistException("Developer not exist");
    }

    checkVersion(version, developer.getVersion());

    actions.stream().forEach(action -> updaterService.handle(developer, action));

    repository.save(developer);

    DeveloperView updatedDeveloper = DeveloperMapper.toModel(developer);

    return updatedDeveloper;
  }

  public Long countDevelopers() {
    logger.debug("Enter.");

    long count = repository.count();

    logger.debug("Exit. developer count: {}.", count);

    return count;
  }

  public List<Developer> getAllDevelopers() {
    logger.debug("Enter.");

    List<Developer> developers = repository.findAll();

    logger.debug("Exit. developer size: {}.", developers.size());

    return developers;
  }

  /**
   * check the version.
   *
   * @param inputVersion Integer
   * @param existVersion Integer
   */
  private void checkVersion(Integer inputVersion, Integer existVersion) {
    if (!inputVersion.equals(existVersion)) {
      logger.debug("Developer version is not correct.");
      throw new ConflictException("Developer version is not correct.");
    }
  }
}
