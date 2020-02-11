package com.fitbod.jroland.controller.user;

import com.fitbod.jroland.api.user.UserRead;
import com.fitbod.jroland.api.user.UserWrite;
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
  public ResponseEntity<Void> upsert(@RequestBody UserWrite user, UriComponentsBuilder componentsBuilder) {
    try {
      userService.upsert(user);
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
  public ResponseEntity<UserRead> getCurrentUser(Authentication authentication) {
    Optional<UserRead> user = AuthenticationUtil.getUserEmail(authentication).flatMap(userService::get);
    return ResponseEntity.of(user);
  }
}
