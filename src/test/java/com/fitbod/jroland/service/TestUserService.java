package com.fitbod.jroland.service;

import com.fitbod.jroland.api.UserApi;
import com.fitbod.jroland.exception.InvalidApiObjectException;
import com.fitbod.jroland.exception.UserExistsException;
import com.fitbod.jroland.persistence.repo.RedisUserRepo;
import com.github.fppt.jedismock.RedisServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Optional;

public class TestUserService {

  private RedisServer redisServer;
  private UserService userService;

  @Before
  public void setup() throws IOException {
    redisServer = RedisServer.newRedisServer();
    redisServer.start();
    Jedis jedis = new Jedis(redisServer.getHost(), redisServer.getBindPort());

    RedisUserRepo redisUserRepo = new RedisUserRepo(jedis);
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(7);
    userService = new UserService(redisUserRepo, passwordEncoder);
  }

  @After
  public void after() {
    redisServer.stop();
  }

  @Test
  public void testCreateAndGetUser() {
    String email = "hellouser@123.com";
    UserApi userApi = new UserApi();
    userApi.setEmail(email);
    userApi.setPassword("password123");

    Optional<UserApi> emptyUser = userService.getObject(email);
    Assert.assertFalse(emptyUser.isPresent());

    UserApi created = userService.create(userApi);
    Assert.assertEquals(userApi.getEmail(), email);
    Assert.assertTrue(StringUtils.isEmpty(created.getPassword()));

    Optional<UserApi> user = userService.getObject(email);
    Assert.assertTrue(user.isPresent());
    Assert.assertEquals(user.get().getEmail(), email);
    Assert.assertTrue(StringUtils.isEmpty(user.get().getPassword()));
  }

  @Test
  public void testCreateUserWithExistingEmail() {
    String email = "hellouser@456.com";

    UserApi userApi1 = new UserApi();
    userApi1.setEmail(email);
    userApi1.setPassword("password1");
    UserApi userApi2 = new UserApi();
    userApi2.setEmail(email);
    userApi2.setPassword("password2");

    userService.create(userApi1);
    boolean exceptionThrown = false;
    try {
      userService.create(userApi2);
    } catch (UserExistsException e) {
      exceptionThrown = true;
    }
    Assert.assertTrue(exceptionThrown);
  }

  @Test(expected = InvalidApiObjectException.class)
  public void testCreateUserNullFields() {
    UserApi invalidUser = new UserApi();
    userService.create(invalidUser);
  }
}
