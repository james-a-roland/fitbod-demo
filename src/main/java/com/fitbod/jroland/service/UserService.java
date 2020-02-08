package com.fitbod.jroland.service;

import com.fitbod.jroland.api.User;
import com.fitbod.jroland.persistence.UserDB;
import com.fitbod.jroland.persistence.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  UserDB userDB;

  @Autowired
  PasswordEncoder passwordEncoder;

  public User registerNewUser(final User user) {
    String email = user.getEmail();
    Optional<User> existingUsers = findByEmail(email);
    if (existingUsers.isPresent()) {
      throw new IllegalArgumentException("Email address " + email + " is already registered!");
    }

    UserModel userModel = UserModel.fromApi(user);
    userDB.createUser(userModel);
    return user;
  }

  public Optional<User> findByEmail(String emailAddress) {
    return userDB.findByEmail(emailAddress).map(UserModel::toApi);
  }

}
