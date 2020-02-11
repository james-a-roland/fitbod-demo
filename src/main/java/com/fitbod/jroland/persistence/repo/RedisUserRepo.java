package com.fitbod.jroland.persistence.repo;

import com.fitbod.jroland.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

@Component
public class RedisUserRepo {

  private final JedisPool jedisPool;

  @Autowired
  public RedisUserRepo(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  private static final String USER_KEY = "USER_KEY";

  public void create(User user) {
    Jedis jedis = jedisPool.getResource();
    try {
      jedisPool.getResource().hset(USER_KEY, user.getEmail(), user.getEncryptedPassword());
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
  }

  public Optional<User> get(String email) {
    Jedis jedis = jedisPool.getResource();
    try {
      return Optional.ofNullable(jedisPool.getResource().hget(USER_KEY, email))
              .map(encryptedPassword -> {
                User user = new User();
                user.setEmail(email);
                user.setEncryptedPassword(encryptedPassword);
                return user;
              });
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }

  }
}
