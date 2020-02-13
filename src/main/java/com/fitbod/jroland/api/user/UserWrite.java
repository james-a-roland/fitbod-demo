package com.fitbod.jroland.api.user;

import com.fitbod.jroland.api.ApiWriteObject;
import com.fitbod.jroland.persistence.model.User;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

public class UserWrite extends ApiWriteObject<User> {

  private static final int MIN_PASSWORD_LENGTH = 8;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String password;

  @Override
  public Optional<String> fetchWriteError() {
    if (!EmailValidator.getInstance().isValid(email)) {
      return Optional.of("Email " + email + " is invalid");
    } else if (StringUtils.isEmpty(password) || password.length() < MIN_PASSWORD_LENGTH) {
      return Optional.of("Password must be at least " + MIN_PASSWORD_LENGTH + " characters.");
    }
    return Optional.empty();
  }

  @Override
  public User toModel() {
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);
    return user;
  }

  @Override
  public String getKey() {
    return email;
  }

  @Override
  public String toString() {
    return ("Email: " + email + " Password: " + password);
  }
}
