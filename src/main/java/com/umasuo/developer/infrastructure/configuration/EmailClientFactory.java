package com.umasuo.developer.infrastructure.configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Factory class for email client.
 */
@Data
@Component
public class EmailClientFactory {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(EmailClientFactory.class);

  /**
   * The region.
   */
  @Value("${aliyun.email.region:cn-hangzhou}")
  private String region;

  /**
   * The access key.
   */
  @Value("${aliyun.email.accessKeyId}")
  private String accessKeyId;

  /**
   * The secret.
   */
  @Value("${aliyun.email.secret}")
  private String secret;

  /**
   * Get aliyun email client.
   *
   * @return IAcsClient
   */
  @Bean
  public IAcsClient getAliyunClient() {
    LOG.debug("Enter.");

    IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, secret);
    IAcsClient client = new DefaultAcsClient(profile);

    LOG.debug("Exit.");

    return client;
  }
}
