package com.fitbod.jroland.auth;

import com.fitbod.jroland.api.User;
import com.fitbod.jroland.service.UserService;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

public class AuthUserDetailsService implements UserDetailsService {

  private static final Set<GrantedAuthority> DEFAULT_AUTHORITIES = ImmutableSet.of();

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userMaybe = userService.findByEmail(username);
    if (!userMaybe.isPresent()) {
      throw new UsernameNotFoundException("No user found with username: " + username);
    }
    User user = userMaybe.get();
    return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                  user.getPassword(),
                                                                  DEFAULT_AUTHORITIES);
  }

}
