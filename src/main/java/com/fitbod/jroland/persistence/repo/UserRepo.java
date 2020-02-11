package com.fitbod.jroland.persistence.repo;

import com.fitbod.jroland.api.user.UserWrite;
import com.fitbod.jroland.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepo extends RedisRepo<UserWrite, User> {

  PasswordEncoder passwordEncoder;

  @Autowired
  public UserRepo(JedisPool jedisPool, PasswordEncoder passwordEncoder) {
    super(jedisPool);
    this.passwordEncoder = passwordEncoder;
  }

  private static final String USER_KEY = "USER_KEY";

  @Override
  protected String upsertImpl(UserWrite writeObj, Jedis jedis) {
    jedis.hset(USER_KEY, writeObj.getKey(), passwordEncoder.encode(writeObj.getPassword()));
    //Throw away the password for security.
    writeObj.setPassword("");
    return writeObj.getKey();
  }

  @Override
  protected List<User> multiReadImpl(List<String> keys, Jedis jedis) {
    String[] keyArray = keys.toArray(new String[keys.size()]);
    List<String> passwords = jedis.hmget(USER_KEY, keyArray);

    List<User> users = new ArrayList<>();
    for (int i = 0; i < keys.size(); i++) {
      User user = new User();
      user.setEmail(keys.get(i));
      user.setPassword(passwords.get(i));
      users.add(user);
    }
    return users.stream().filter(user -> !StringUtils.isEmpty(user.getPassword())).collect(Collectors.toList());
  }

}
