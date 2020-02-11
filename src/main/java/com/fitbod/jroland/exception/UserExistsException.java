package com.fitbod.jroland.exception;

import com.fitbod.jroland.api.user.UserWrite;

public class UserExistsException extends RuntimeException {

  public UserExistsException(UserWrite user) {
    super("User with key " + user.getKey() + " already exists!");
  }
}
