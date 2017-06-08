package com.umasuo.developer.infrastructure.repository;

import com.umasuo.developer.domain.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, String>{

  /**
   * Find one by email.
   *
   * @param email the email
   * @return the developer
   */
  Developer findOneByEmail(String email);

  /**
   * Find by openable.
   *
   * @param openable the openable
   * @return the list
   */
  List<Developer> findByOpenable(Boolean openable);
}
