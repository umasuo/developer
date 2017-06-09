package com.umasuo.developer.application.dto;

import com.umasuo.developer.application.dto.Reference;
import com.umasuo.developer.infrastructure.enums.ReplyRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Created by Davis on 17/6/9.
 */
@Setter
@Getter
@ToString
public class ResourceRequestView {

  /**
   * The request id.
   */
  private String id;

  /**
   * The Created at.
   */
  private ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  private ZonedDateTime lastModifiedAt;

  /**
   * Version used for update date check.
   */
  private Integer version;

  /**
   * The Acceptor id.
   */
  private String acceptorId;

  /**
   * The Reply request.
   */
  private ReplyRequest replyRequest;

  /**
   * The Applicant id.
   */
  private String applicantId;

  /**
   * The Applicant viewed.
   */
  private Boolean applicantViewed;

  /**
   * The Device id.
   */
  private String deviceId;

  /**
   * The References.
   */
  private List<Reference> references;
}
