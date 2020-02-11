package com.fitbod.jroland.persistence.model;

import com.fitbod.jroland.api.user.UserRead;
import com.fitbod.jroland.api.user.UserWrite;
import lombok.Getter;
import lombok.Setter;

public class User implements PersistentObj<UserRead, UserWrite> {

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String password;

  @Override
  public UserRead toReadObject() {
    UserRead read = new UserRead();
    read.setEmail(email);
    return read;
  }
}
