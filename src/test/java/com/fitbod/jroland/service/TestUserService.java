package com.fitbod.jroland.service;

import com.fitbod.jroland.api.user.UserRead;
import com.fitbod.jroland.api.user.UserWrite;
import com.fitbod.jroland.exception.InvalidApiObjectException;
import com.fitbod.jroland.exception.UserExistsException;
import com.fitbod.jroland.persistence.repo.UserRepo;
import com.github.fppt.jedismock.RedisServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Optional;

public class TestUserService {

  private RedisServer redisServer;
  private UserService userService;

  @Before
  public void setup() throws IOException {
    redisServer = RedisServer.newRedisServer();
    redisServer.start();

    JedisPool jedisPool = new JedisPool(redisServer.getHost(), redisServer.getBindPort());
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(7);
    UserRepo userRepo = new UserRepo(jedisPool, passwordEncoder);
    userService = new UserService(userRepo);
  }

  @After
  public void after() {
    redisServer.stop();
  }

  @Test
  public void testUpsertAndGetUser() {
    String email = "hellouser@123.com";
    UserWrite userWrite = new UserWrite();
    userWrite.setEmail(email);
    userWrite.setPassword("password123");

    Optional<UserRead> retrievedUser = userService.getObject(email);
    Assert.assertFalse(retrievedUser.isPresent());

    String created = userService.upsert(userWrite);
    Assert.assertTrue(StringUtils.isEmpty(userWrite.getPassword()));
    retrievedUser = userService.getObject(email);
    Assert.assertTrue(retrievedUser.isPresent());
    Assert.assertEquals(retrievedUser.get().getEmail(), email);
    Assert.assertEquals(email, created);
  }

  @Test
  public void testCreateUserWithExistingEmail() {
    String email = "hellouser@456.com";

    UserWrite userWrite1 = new UserWrite();
    userWrite1.setEmail(email);
    userWrite1.setPassword("password1");
    UserWrite userWrite2 = new UserWrite();
    userWrite2.setEmail(email);
    userWrite2.setPassword("password2");

    userService.upsert(userWrite1);
    boolean exceptionThrown = false;
    try {
      userService.upsert(userWrite2);
    } catch (UserExistsException e) {
      exceptionThrown = true;
    }
    Assert.assertTrue(exceptionThrown);
  }

  @Test(expected = InvalidApiObjectException.class)
  public void testCreateInvalidUser() {
    UserWrite invalidUser = new UserWrite();
    invalidUser.setEmail("invalid email");
    invalidUser.setPassword("2short");
    userService.upsert(invalidUser);
  }
}
