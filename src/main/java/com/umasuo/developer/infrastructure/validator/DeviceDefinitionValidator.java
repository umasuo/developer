package com.umasuo.developer.infrastructure.validator;

import com.google.common.collect.Lists;
import com.umasuo.developer.application.dto.DeviceDefinitionView;
import com.umasuo.developer.application.dto.Reference;
import com.umasuo.developer.application.dto.ResourceRequestDraft;
import com.umasuo.developer.infrastructure.util.ReferenceType;
import com.umasuo.exception.ParametersException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

/**
 * Device definition validator.
 */
public final class DeviceDefinitionValidator {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeviceDefinitionValidator.class);

  /**
   * Private default constructor.
   */
  private DeviceDefinitionValidator() {
  }

  /**
   * Validate.
   *
   * @param deviceDefinition the device definition
   * @param request          the request
   */
  public static void validate(DeviceDefinitionView deviceDefinition, ResourceRequestDraft request) {
    // 检查是否开放设备
    if (!deviceDefinition.getOpenable()) {
      LOGGER.debug("DeviceDefinition: {} is not open.", deviceDefinition.getId());
      throw new ParametersException("DeviceDefinition is not open");
    }

    // 获取所有的data definition
    List<String> dataDefinitions = Lists.newArrayList();
    Consumer<Reference> consumer = reference -> {
      if (reference.getType().equals(ReferenceType.DATA_DEFINITION)) {
        dataDefinitions.add(reference.getId());
      }
    };
    request.getReferences().stream().forEach(consumer);

    dataDefinitions.removeAll(deviceDefinition.getDataDefineIds());
    if (!dataDefinitions.isEmpty()) {
      LOGGER.debug("Can not find dataDefinition: {} in deviceDefinition: {}.", dataDefinitions,
        deviceDefinition.getId());
      throw new ParametersException("DataDefinition can not find");
    }
  }
}
