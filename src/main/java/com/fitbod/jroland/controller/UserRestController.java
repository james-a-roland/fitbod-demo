package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.User;
import com.fitbod.jroland.service.UserService;
import com.fitbod.jroland.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

  @Autowired
  UserService userService;

  /**
   * Register a new user. No authentication is required.
   */
  @PostMapping
  public ResponseEntity<Void> create(@RequestBody User user, UriComponentsBuilder componentsBuilder) {
    return ResponseEntity.created(componentsBuilder.path("/api/user").build(1)).build();
  }

  /**
   * Get the currently logged in user.
   */
  @GetMapping
  public ResponseEntity<User> getCurrentUser(Authentication authentication) {
    Optional<User> user = AuthenticationUtil.getUserEmail(authentication).flatMap(userService::findByEmail);
    return ResponseEntity.of(user);
  }
}
