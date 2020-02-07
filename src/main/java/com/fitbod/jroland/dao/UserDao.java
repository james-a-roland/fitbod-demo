package com.fitbod.jroland.dao;

import com.fitbod.jroland.model.User;

import java.util.Optional;

public class UserDao {

  public Optional<User> getUser(String email) {
    User user = new User();
    user.setEmail(email);
    return Optional.of(user);
  }

}
