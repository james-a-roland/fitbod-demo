package com.fitbod.jroland.config;

import com.fitbod.jroland.api.User;
import com.fitbod.jroland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootupListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    User defaultUser = new User();
    defaultUser.setEmail("jr");
    defaultUser.setPassword(passwordEncoder.encode("pass"));
    userService.registerNewUser(defaultUser);
  }
}
