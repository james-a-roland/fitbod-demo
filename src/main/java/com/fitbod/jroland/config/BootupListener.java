package com.fitbod.jroland.config;

import com.fitbod.jroland.api.UserApi;
import com.fitbod.jroland.exception.UserExistsException;
import com.fitbod.jroland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BootupListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  UserService userService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    UserApi defaultUser = new UserApi();
    defaultUser.setEmail("jr@test.com");
    defaultUser.setPassword("password1");
    try {
//      Create a non-default user while debugging to avoid re-registration.
//      userService.create(defaultUser);
    } catch (UserExistsException e) {
    }
  }
}
