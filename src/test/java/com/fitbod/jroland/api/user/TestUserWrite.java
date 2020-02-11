package com.fitbod.jroland.api.user;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class TestUserWrite {

  @DataProvider
  public static Object[][] validWrite() {
    UserWrite user1 = new UserWrite();
    user1.setEmail("validEmail@email.com");
    user1.setPassword("longEnoughPassword");

    return new Object[][]{
            {user1}
    };
  }

  @DataProvider
  public static Object[][] invalidWrite() {
    UserWrite emptyUser = new UserWrite();

    UserWrite invalidEmail = new UserWrite();
    invalidEmail.setEmail("invalidEmail");
    invalidEmail.setPassword("validPassword123");

    UserWrite noPassword = new UserWrite();
    noPassword.setEmail("validEmail@email.com");

    UserWrite invalidPassword = new UserWrite();
    invalidPassword.setEmail("validEmail@email.com");
    invalidPassword.setPassword("2short");

    return new Object[][]{
            {emptyUser}, {invalidEmail}, {noPassword}, {invalidPassword}
    };
  }

  @Test
  @UseDataProvider("validWrite")
  public void testValidWrite(UserWrite user) {
    Assert.assertTrue(user.canBeWritten());
  }

  @Test
  @UseDataProvider("invalidWrite")
  public void testInvalidWrite(UserWrite user) {
    Assert.assertFalse(user.canBeWritten());
  }
}

