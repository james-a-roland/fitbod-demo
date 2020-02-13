package com.fitbod.jroland.util;

import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class AuthenticationUtil {

  public static boolean isAuthenticated(Authentication authentication) {
    return authentication != null && authentication.isAuthenticated();
  }

  public static void verifyAuthenticated(Authentication authentication) {
    if (!isAuthenticated(authentication)) {
      throw new IllegalStateException("Unauthenticated users cannot access this resource.");
    }
  }

  public static Optional<String> getUserEmail(Authentication authentication) {
    if (isAuthenticated(authentication) && !StringUtils.isEmpty(authentication.getName())) {
      return Optional.ofNullable(authentication.getName());
    }
    return Optional.empty();
  }

  public static String getUserEmailUnsafe(Authentication authentication) {
    verifyAuthenticated(authentication);
    return getUserEmail(authentication).get();
  }
}
