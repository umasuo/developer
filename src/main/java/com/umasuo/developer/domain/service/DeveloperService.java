package com.umasuo.developer.domain.service;

import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.application.dto.mapper.DeveloperMapper;
import com.umasuo.developer.domain.model.Developer;
import com.umasuo.developer.infrastructure.enums.AccountStatus;
import com.umasuo.developer.infrastructure.repository.DeveloperRepository;
import com.umasuo.developer.infrastructure.util.PasswordUtil;
import com.umasuo.developer.infrastructure.validator.VersionValidator;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

  /**
   * create a developer from an sample.
   *
   * @param developer the sample
   * @return result. developer
   */
  public Developer create(Developer developer) {
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

    return create(developer);
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
   * change password.
   *
   * @param id      developer id.
   * @param version current version
   * @param oldPwd  old password
   * @param newPwd  new password
   * @return developer
   */
  public Developer changePassword(String id, Integer version, String oldPwd, String newPwd) {
    Developer developer = this.get(id);
    Assert.notNull(developer);

    //TODO check status

    boolean isOldPwdRight = PasswordUtil.checkPassword(oldPwd, developer.getPassword());
    if (!isOldPwdRight) {
      //TODO throw exception
      //throw new Password
    }
    developer.setVersion(version);
    developer.setPassword(PasswordUtil.hashPassword(newPwd));
    return this.repository.save(developer);
  }

  /**
   * forget password, send an reset email to the developer for reset password.
   *
   * @param email email
   */
  public void forgetPassword(String email) {
    //TODO send the email.
  }

  /**
   * reset password with the reset token.
   *
   * @param email email
   * @param token reset password token.
   */
  public void resetPassword(String email, String token) {
    //TODO check token
    //getAllForApplicant developer
    // reset the password
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

  /**
   * Update developer open status.
   *
   * @param id       the id
   * @param version  the version
   * @param openable the openable
   * @return the developer view
   */
  @Transactional
  public DeveloperView updateOpenStatus(String id, Integer version, Boolean openable) {
    logger.debug("Enter. developerId: {}, version: {}, openable: {}.", id, version, openable);

    Developer developer = repository.findOne(id);
    VersionValidator.validate(developer.getVersion(), version);

    developer.setOpenable(openable);

    Developer updatedDeveloper = repository.save(developer);

    DeveloperView result = DeveloperMapper.toModel(updatedDeveloper);

    logger.trace("Updated developer: {}.", result);
    logger.debug("Exit.");
    return result;
  }
}
