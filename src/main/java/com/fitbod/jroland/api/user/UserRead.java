package com.fitbod.jroland.api.user;

import com.fitbod.jroland.api.ApiReadObject;
import com.fitbod.jroland.persistence.model.User;
import lombok.Getter;
import lombok.Setter;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

public class UserRead extends ApiReadObject<User> {

  @Getter
  @Setter
  String email;

  @Override
  public Optional<String> fetchReadError() {
    if (StringUtils.isEmpty(email)) {
      return Optional.of("Email must be populated!");
    }
    return Optional.empty();
  }
}

