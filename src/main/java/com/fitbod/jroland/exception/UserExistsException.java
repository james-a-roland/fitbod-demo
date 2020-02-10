package com.fitbod.jroland.exception;

import com.fitbod.jroland.api.UserApi;
import com.fitbod.jroland.persistence.model.User;

public class UserExistsException extends RuntimeException {

  public UserExistsException(UserApi user) {
    super("User with email " + user.getEmail() + " already exists!");
  }
}
