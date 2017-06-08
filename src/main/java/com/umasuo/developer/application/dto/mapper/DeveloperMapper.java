package com.umasuo.developer.application.dto.mapper;

import com.google.api.client.util.Lists;
import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.domain.model.Developer;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by umasuo on 17/3/7.
 */
public class DeveloperMapper {

  public static List<DeveloperView> toModel(List<Developer> entities) {
    List<DeveloperView> models = Lists.newArrayList();

    Consumer<Developer> consumer = developer -> models.add(toModel(developer));

    entities.stream().forEach(consumer);

    return models;
  }

  public static DeveloperView toModel(Developer developer) {
    DeveloperView view = null;
    if (developer != null) {
      view = new DeveloperView();
      view.setId(developer.getId());
      view.setEmail(developer.getEmail());
      view.setVersion(developer.getVersion());
      view.setCreatedAt(developer.getCreatedAt());
      view.setLastModifiedAt(developer.getLastModifiedAt());
      view.setPhone(developer.getPhone());
      view.setStatus(developer.getStatus());
      view.setOpenable(developer.getOpenable());
    }
    return view;
  }

  public static Developer toEntity(DeveloperView developer) {
    Developer model = null;
    if (developer != null) {
      model = new Developer();
      model.setId(developer.getId());
      model.setEmail(developer.getEmail());
      model.setVersion(developer.getVersion());
      model.setCreatedAt(developer.getCreatedAt());
      model.setLastModifiedAt(developer.getLastModifiedAt());
      model.setPhone(developer.getPhone());
      model.setStatus(developer.getStatus());
    }
    return model;
  }
}
