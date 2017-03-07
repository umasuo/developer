package com.umasuo.developer.application.dto.map;

import com.umasuo.developer.application.dto.DeveloperView;
import com.umasuo.developer.domain.model.Developer;

/**
 * Created by umasuo on 17/3/7.
 */
public class DeveloperMapper {

  public static DeveloperView modelToView(Developer developer) {
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
    }
    return view;
  }

  public static Developer viewToModel(DeveloperView developer) {
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
