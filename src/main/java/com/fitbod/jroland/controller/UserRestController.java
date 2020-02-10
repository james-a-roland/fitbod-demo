package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.UserApi;
import com.fitbod.jroland.exception.InvalidApiObjectException;
import com.fitbod.jroland.exception.UserExistsException;
import com.fitbod.jroland.service.UserService;
import com.fitbod.jroland.util.AuthenticationUtil;
import com.fitbod.jroland.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(RouteUtil.API_USER)
public class UserRestController {

  @Autowired
  UserService userService;

  /**
   * Register a new user. No authentication is required.
   */
  @PostMapping
  public ResponseEntity<Void> create(@RequestBody UserApi user, UriComponentsBuilder componentsBuilder) {
    try {
      userService.create(user);
      return ResponseEntity.ok().build();
    } catch (InvalidApiObjectException e) {
      System.out.println(e);
      return ResponseEntity.badRequest().build();
    } catch (UserExistsException e) {
      System.out.println(e);
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  /**
   * Get the currently logged in user.
   */
  @GetMapping
  public ResponseEntity<UserApi> getCurrentUser(Authentication authentication) {
    Optional<UserApi> user = AuthenticationUtil.getUserEmail(authentication).flatMap(userService::get);
    return ResponseEntity.of(user);
  }
}
