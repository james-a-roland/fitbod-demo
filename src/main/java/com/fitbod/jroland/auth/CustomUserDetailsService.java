package com.fitbod.jroland.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

public class CustomUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println(("TODO implement me 2"));
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);
    final String commonPass = encoder.encode("pass");
    Set<GrantedAuthority> authorities = new HashSet<>();
    return new org.springframework.security.core.userdetails.User(username, commonPass, authorities);
  }

}
