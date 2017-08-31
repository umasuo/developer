package com.umasuo.developer.domain.model;

import com.umasuo.developer.infrastructure.enums.AccountStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Developer model.
 */
@Entity
@Table(name = "developer")
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Developer {

  /**
   * Developer id in uuid.
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
  @Column(name = "created_at")
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  protected Long lastModifiedAt;

  /**
   * version used for update date check.
   */
  @Version
  private Integer version;

  /**
   * email of the developer,
   */
  @Column(unique = true)
  private String email;

  /**
   * phone number.
   */
  private String phone;

  /**
   * encrypted password
   */
  private String password;

  /**
   * developer status: unverified, verified, disabled.
   * 当disabled之后，该用户所发售的设备将不再能够接入云端，此操作需要做几步验证。
   */
  private AccountStatus status;

  /**
   * Openable means other developer can find this developer or not.
   * If true, this developer can be read by
   */
  private Boolean openable = false;

  /**
   * To string method.
   *
   * @return
   */
  @Override
  public String toString() {
    return "Developer{"
      + "id='" + id + '\''
      + ", createdAt=" + createdAt
      + ", lastModifiedAt=" + lastModifiedAt
      + ", version=" + version
      + ", email='" + email + '\''
      + ", phone='" + phone + '\''
      + ", openable='" + openable + '\''
      + '}';
  }
}
