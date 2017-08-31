package com.umasuo.developer.application.dto.mapper;

import com.google.api.client.util.Lists;
import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.domain.model.Developer;

import java.util.List;
import java.util.function.Consumer;

/**
 * Developer mapper.
 */
public final class DeveloperMapper {

  /**
   * Private default constructor.
   */
  private DeveloperMapper() {
  }

  /**
   * Model list to view list.
   *
   * @param entities
   * @return
   */
  public static List<DeveloperView> toView(List<Developer> entities) {
    List<DeveloperView> models = Lists.newArrayList();

    Consumer<Developer> consumer = developer -> models.add(toView(developer));

    entities.stream().forEach(consumer);

    return models;
  }

  /**
   * Model to view.
   *
   * @param developer
   * @return
   */
  public static DeveloperView toView(Developer developer) {
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

  /**
   * View to model.
   *
   * @param developer
   * @return
   */
  public static Developer toModel(DeveloperView developer) {
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
