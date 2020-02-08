package com.fitbod.jroland.util;

import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class AuthenticationUtil {

  public static Optional<String> getUser(Authentication authentication) {
    if (isValidAuthentication(authentication)) {
      return Optional.ofNullable(authentication.getName());
    }
    return Optional.empty();
  }

  private static boolean isValidAuthentication(Authentication authentication) {
    return authentication != null && authentication.isAuthenticated() && (!StringUtils.isEmpty(authentication.getName()));
  }

}
