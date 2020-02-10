package com.fitbod.jroland.api;

import com.fitbod.jroland.persistence.model.User;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

public class UserApi implements ApiObject<User> {

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
    } else if (!isCompletePassword(password)) {
      return Optional.of("Password must be at least " + MIN_PASSWORD_LENGTH + " characters.");
    }
    return Optional.empty();
  }

  @Override
  public Optional<String> fetchReadError() {
    if (StringUtils.isEmpty(email)) {
      return Optional.of("Email must be populated!");
    } else if (!StringUtils.isEmpty(password)) {
      return Optional.of("Passwords should not publicly read!");
    }
    return Optional.empty();
  }

  private static boolean isCompletePassword(String password) {
    return !StringUtils.isEmpty(password) && password.length() >= MIN_PASSWORD_LENGTH;
  }
}
