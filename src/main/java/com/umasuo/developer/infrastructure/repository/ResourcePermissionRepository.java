package com.umasuo.developer.infrastructure.repository;

import com.umasuo.developer.domain.model.ResourcePermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Created by Davis on 17/6/9.
 */
public interface ResourcePermissionRepository extends JpaRepository<ResourcePermission, String>,
    QueryByExampleExecutor<ResourcePermission> {

}
