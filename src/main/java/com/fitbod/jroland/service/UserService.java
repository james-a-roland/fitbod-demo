package com.fitbod.jroland.service;

import com.fitbod.jroland.api.UserApi;
import com.fitbod.jroland.exception.UserExistsException;
import com.fitbod.jroland.persistence.model.User;
import com.fitbod.jroland.persistence.repo.RedisUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends PersistentService<UserApi> {

  private final RedisUserRepo redisUserRepo;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(RedisUserRepo redisUserRepo, PasswordEncoder passwordEncoder) {
    this.redisUserRepo = redisUserRepo;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected UserApi createObject(UserApi userApi) {
    if (getObject(userApi.getEmail()).isPresent()) {
      throw new UserExistsException(userApi);
    }
    redisUserRepo.create(User.fromApi(userApi, passwordEncoder));

    //Do not return any password back to the client!
    userApi.setPassword("");
    return userApi;
  }

  @Override
  protected Optional<UserApi> getObject(String key) {
    return redisUserRepo.get(key).map(User::toApi);
  }

}
