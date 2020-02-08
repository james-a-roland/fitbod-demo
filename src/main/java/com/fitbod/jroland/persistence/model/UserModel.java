package com.fitbod.jroland.persistence.model;

import com.fitbod.jroland.api.User;
import lombok.Getter;
import lombok.Setter;

public class UserModel {

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String password;

  public static UserModel fromApi(User user) {
    UserModel model = new UserModel();
    model.setEmail(user.getEmail());
    model.setPassword(user.getPassword());
    return model;
  }

  public User toApi() {
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);
    return user;
  }

}
