package com.fitbod.jroland.service;

import com.fitbod.jroland.api.User;
import com.fitbod.jroland.persistence.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  UserDB userDB;

  public User registerNewUser(final User user) {
    String email = user.getEmail();
    Optional<User> existingUsers = findByEmail(email);
    if (existingUsers.isPresent()) {
      throw new IllegalArgumentException("Email address " + email + " is already registered!");
    }

    userDB.createUser(user);
    return user;
  }

  public Optional<User> findByEmail(String emailAddress) {
    return userDB.findByEmail(emailAddress);
  }

}
