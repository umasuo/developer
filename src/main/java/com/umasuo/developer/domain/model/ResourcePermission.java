package com.umasuo.developer.domain.model;

import com.umasuo.database.dialect.JSONBUserType;
import com.umasuo.developer.application.dto.Reference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Created by Davis on 17/6/9.
 */
@Data
@Entity
@Table(name = "resource_permission")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "List", typeClass = JSONBUserType.class, parameters = {
    @Parameter(name = JSONBUserType.CLASS, value = "java.util.List")}
)
public class ResourcePermission {

  /**
   * The id.
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * The Created at.
   */
  @CreatedDate
  private Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  private Long lastModifiedAt;

  /**
   * version used for update date check.
   */
  @Version
  private Integer version;

  /**
   * The Applicant id.
   */
  private String applicantId;

  /**
   * The Acceptor id.
   */
  private String acceptorId;

  /**
   * The Device id.
   */
  private String deviceDefinitionId;

  /**
   * The References.
   */
  @Column(name = "data_references")
  @Type(type = "List")
  private List<Reference> references;
}
