package com.fitbod.jroland.persistence;

import com.fitbod.jroland.persistence.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDB {

  private Map<String, String> users = new HashMap<>();


  public UserModel createUser(UserModel userModel) {
    users.put(userModel.getEmail(), userModel.getPassword());
    return userModel;
  }

  public Optional<UserModel> findByEmail(String email) {
    if (!users.containsKey(email)) {
      return Optional.empty();
    } else {
      UserModel model = new UserModel();
      model.setEmail(email);
      model.setPassword(users.get(email));
      return Optional.of(model);
    }
  }

}
