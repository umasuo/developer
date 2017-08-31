package com.umasuo.developer.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Device definition view.
 */
@Data
public class DeviceDefinitionView implements Serializable {
  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = -8662294173374891858L;

  /**
   * Device definition id.
   */
  private String id;

  /**
   * The Created at.
   */
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  protected Long lastModifiedAt;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * which developer this kind of device belong to.
   */
  private String developerId;

  /**
   * device status: published, unpublished.
   */
  private DeviceStatus status;

  /**
   * name of the device.
   */
  private String name;

  /**
   * device icon.
   */
  private String icon;

  /**
   * 数据定义ID，需要提前定义好不同的数据类型.
   */
  private List<String> dataDefineIds;

  /**
   * device type, identify by how the communicate with other services(app, cloud)
   */
  private DeviceType type;

  /**
   * Open status about this device.
   * True means this device can be find by other developers and false means not.
   */
  private Boolean openable = false;
}

/**
 * Device status.
 */
enum DeviceStatus {
  /**
   * Unpublished.
   */
  UNPUBLISHED,
  /**
   * Published.
   */
  PUBLISHED
}

/**
 * Device type.
 */
enum DeviceType {
  /**
   * Blue tooth.
   */
  BLUETOOTH,
  /**
   * Wifi.
   */
  WIFI,
  /**
   * GPRS.
   */
  GPRS
}