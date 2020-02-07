package com.fitbod.jroland.auth;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

public class UserAuthenticationProvider extends DaoAuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    System.out.println(("TODO implement me 1"));
    return super.authenticate(authentication);
  }

}
