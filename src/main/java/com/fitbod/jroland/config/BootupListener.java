package com.fitbod.jroland.config;

import com.fitbod.jroland.api.user.UserWrite;
import com.fitbod.jroland.exception.UserExistsException;
import com.fitbod.jroland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BootupListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  UserService userService;

  @Autowired
  Environment environment;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    Boolean isDev = Optional.ofNullable(environment.getProperty("isDev")).map(Boolean::valueOf).orElse(false);
    if (isDev) {
      // Create a non-default user to avoid re-registration in test environments.
      UserWrite defaultUser = new UserWrite();
      defaultUser.setEmail("jr@test.com");
      defaultUser.setPassword("password1");
      try {
        userService.upsert(defaultUser);
        System.out.println("Created new default user: " + defaultUser);
      } catch (UserExistsException e) {
        System.out.println("Did not create default user " + defaultUser + " as it already exists!");
      }
    }
  }
}
