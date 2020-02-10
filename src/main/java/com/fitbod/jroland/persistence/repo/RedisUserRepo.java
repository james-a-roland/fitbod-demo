package com.fitbod.jroland.persistence.repo;

import com.fitbod.jroland.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Component
public class RedisUserRepo {

  private final Jedis jedis;

  @Autowired
  public RedisUserRepo(Jedis jedis) {
    this.jedis = jedis;
  }

  private static final String USER_KEY = "USER_KEY";

  public void create(User user) {
    jedis.hset(USER_KEY, user.getEmail(), user.getEncryptedPassword());
  }

  public Optional<User> get(String email) {
    return Optional.ofNullable(jedis.hget(USER_KEY, email))
            .map(encryptedPassword -> {
              User user = new User();
              user.setEmail(email);
              user.setEncryptedPassword(encryptedPassword);
              return user;
            });
  }
}
