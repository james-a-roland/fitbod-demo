package com.fitbod.jroland.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.fitbod.jroland.util.RouteConstants.HOME;
import static com.fitbod.jroland.util.RouteConstants.LOGIN;
import static com.fitbod.jroland.util.RouteConstants.REGISTER;
import static com.fitbod.jroland.util.RouteConstants.WORKOUT;

@Configuration
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
    auth.authenticationProvider(getAuthenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests().antMatchers(formatRouteName(WORKOUT)).authenticated().and()
        .authorizeRequests().antMatchers(formatRouteName(HOME), formatRouteName(LOGIN), formatRouteName(REGISTER), "/successRegister*").permitAll().and()
        .formLogin().permitAll();
  }

  private String formatRouteName(String name) {
    return name + "*";
  }

  @Bean
  public DaoAuthenticationProvider getAuthenticationProvider() {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(getUserDetailsService());
    authenticationProvider.setPasswordEncoder(getPasswordEncoder());
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder(13);
  }

  @Bean
  public UserDetailsService getUserDetailsService() {
    return new AuthUserDetailsService();
  }

}
