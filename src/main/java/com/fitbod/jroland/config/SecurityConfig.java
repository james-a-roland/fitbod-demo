package com.fitbod.jroland.config;

import com.fitbod.jroland.auth.CustomUserDetailsService;
import com.fitbod.jroland.auth.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
    auth.authenticationProvider(getAuthenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/*", "/login*", "/register*", "/successRegister*").permitAll().and()
        .formLogin().permitAll();
  }

  @Bean
  public DaoAuthenticationProvider getAuthenticationProvider() {
    final UserAuthenticationProvider authenticationProvider = new UserAuthenticationProvider();
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
    return new CustomUserDetailsService();
  }

}
