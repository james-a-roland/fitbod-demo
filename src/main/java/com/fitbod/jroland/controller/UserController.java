package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.User;
import com.fitbod.jroland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

  private final static User ANONYMOUS_USER = new User();
  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("/register")
  public String showRegistrationForm(WebRequest request, Authentication authentication, Model model) {
    model.addAttribute("user", ANONYMOUS_USER);
    return "register";
  }

  @PostMapping("/register")
  public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid User user,
                                          BindingResult result, WebRequest request, Errors errors) {
    //Encrypt the password ASAP for application use.
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    if (result.hasErrors()) {
      return new ModelAndView("register", "user", ANONYMOUS_USER);
    } else {
      User createdUser = userService.registerNewUser(user);
      return new ModelAndView("successRegister", "user", createdUser);
    }
  }

}
