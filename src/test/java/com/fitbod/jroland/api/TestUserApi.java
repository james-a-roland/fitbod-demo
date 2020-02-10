package com.fitbod.jroland.api;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(DataProviderRunner.class)
public class TestUserApi {

  @DataProvider
  public static Object[][] validRead() {
    UserApi user1 = new UserApi();
    user1.setEmail("validEmail@email.com");

    return new Object[][]{
            {user1}
    };
  }

  @DataProvider
  public static Object[][] validWrite() {
    UserApi user1 = new UserApi();
    user1.setEmail("validEmail@email.com");
    user1.setPassword("longEnoughPassword");

    return new Object[][]{
            {user1}
    };
  }

  @DataProvider
  public static Object[][] invalidRead() {
    UserApi user1 = new UserApi();

    UserApi user2 = new UserApi();
    user2.setPassword("mypassword123");

    return new Object[][]{
            {user1}, {user2}
    };
  }

  @DataProvider
  public static Object[][] invalidWrite() {
    UserApi user1 = new UserApi();

    UserApi user2 = new UserApi();
    user2.setEmail("invalidEmail");
    user2.setPassword("validPassword123");

    UserApi user3 = new UserApi();
    user3.setEmail("validEmail@email.com");

    UserApi user4 = new UserApi();
    user4.setEmail("invalidEmail");
    user4.setPassword("2short");

    return new Object[][]{
            {user1}, {user2}, {user3}, {user4}
    };
  }

  @Test
  @UseDataProvider("validRead")
  public void testValidRead(UserApi userApi) {
    Optional<String> readErr = userApi.fetchReadError();
    Assert.assertFalse(readErr.isPresent());
  }

  @Test
  @UseDataProvider("validWrite")
  public void testValidWrite(UserApi userApi) {
    Optional<String> writeErr = userApi.fetchWriteError();
    Assert.assertFalse(writeErr.isPresent());
  }

  @Test
  @UseDataProvider("invalidRead")
  public void testInvalidRead(UserApi userApi) {
    Optional<String> readErr = userApi.fetchReadError();
    Assert.assertTrue(readErr.isPresent());
  }

  @Test
  @UseDataProvider("invalidWrite")
  public void testInvalidWrite(UserApi userApi) {
    Optional<String> readErr = userApi.fetchWriteError();
    Assert.assertTrue(readErr.isPresent());
  }
}

