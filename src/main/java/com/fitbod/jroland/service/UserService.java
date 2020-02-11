package com.fitbod.jroland.service;

import com.fitbod.jroland.api.user.UserRead;
import com.fitbod.jroland.api.user.UserWrite;
import com.fitbod.jroland.exception.UserExistsException;
import com.fitbod.jroland.persistence.model.User;
import com.fitbod.jroland.persistence.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends PersistentService<UserRead, UserWrite> {

  private final UserRepo userRepo;

  @Autowired
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  protected String upsertObject(UserWrite toUpsert) {
    if (getObject(toUpsert.getKey()).isPresent()) {
      throw new UserExistsException(toUpsert);
    }

    return userRepo.upsert(toUpsert);
  }

  @Override
  protected Optional<UserRead> getObject(String key) {
    return userRepo.get(key).map(User::toReadObject).filter(UserRead::canBeRead);
  }

}
