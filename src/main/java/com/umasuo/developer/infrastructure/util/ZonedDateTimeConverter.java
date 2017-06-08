package com.umasuo.developer.infrastructure.util;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Davis on 17/6/1.
 */
@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, String> {


  @Override
  public String convertToDatabaseColumn(ZonedDateTime attribute) {
    ZonedDateTime localUTC = ZonedDateTime.ofInstant(attribute.toInstant(), ZoneOffset.UTC);
    return localUTC.toString();
  }

  @Override
  public ZonedDateTime convertToEntityAttribute(String dbData) {
    ZoneId utcZoneId = ZonedDateTime.now(ZoneOffset.UTC).getZone();


    ZonedDateTime dateTime =
        ZonedDateTime.ofInstant(ZonedDateTime.parse(dbData).toInstant(), utcZoneId);

    return dateTime;
  }
}
