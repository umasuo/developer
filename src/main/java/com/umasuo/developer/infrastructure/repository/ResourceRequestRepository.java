package com.umasuo.developer.infrastructure.repository;

import com.umasuo.developer.domain.model.ResourceRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Davis on 17/6/9.
 */
public interface ResourceRequestRepository extends JpaRepository<ResourceRequest, String>{

  /**
   * Find by applicant id.
   *
   * @param applicantId the applicant id
   * @return the list
   */
  List<ResourceRequest> findByApplicantId(String applicantId);

  /**
   * Find by acceptor id.
   *
   * @param acceptorId the acceptor id
   * @return the list
   */
  List<ResourceRequest> findByAcceptorId(String acceptorId);
}
