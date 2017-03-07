package com.umasuo.developer.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by umasuo on 17/3/6.
 */
@Entity
@Table(name = "developer")
@Setter
@Getter
public class Developer {

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
  protected ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  protected ZonedDateTime lastModifiedAt;

  /**
   * version used for update date check.
   */
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
   */
  private short status;

  // TODO add company info. for verification.
  //private String company;


  @Override
  public String toString() {
    return "Developer{"
        + "id='" + id + '\''
        + ", createdAt=" + createdAt
        + ", lastModifiedAt=" + lastModifiedAt
        + ", version=" + version
        + ", email='" + email + '\''
        + ", phone='" + phone + '\''
        + '}';
  }
}
