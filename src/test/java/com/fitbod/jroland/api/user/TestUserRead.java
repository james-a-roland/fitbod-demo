package com.fitbod.jroland.api.user;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class TestUserRead {

  @DataProvider
  public static Object[][] validRead() {
    UserRead user1 = new UserRead();
    user1.setEmail("validEmail@email.com");

    return new Object[][]{
            {user1}
    };
  }

  @DataProvider
  public static Object[][] invalidRead() {
    UserRead emptyUser = new UserRead();

    return new Object[][]{
            {emptyUser}
    };
  }

  @Test
  @UseDataProvider("validRead")
  public void testValidRead(UserRead user) {
    Assert.assertTrue(user.canBeRead());
  }

  @Test
  @UseDataProvider("invalidRead")
  public void testInvalidRead(UserRead user) {
    Assert.assertFalse(user.canBeRead());
  }
}
