package com.fitbod.jroland.controller;

import com.fitbod.jroland.model.User;
import org.springframework.security.core.Authentication;
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
import java.security.Principal;

@Controller
public class UserController {

  @GetMapping("/register")
  public String showRegistrationForm(WebRequest request, Authentication authentication, Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "register";
  }

  @PostMapping("/register")
  public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid User user,
                                          BindingResult result, WebRequest request, Errors errors) {
    User registered = new User();
    if (!result.hasErrors()) {
      registered = createUserAccount(user);
    }
    if (registered == null) {
      result.rejectValue("email", "message.regError");
    }
    if (result.hasErrors()) {
      return new ModelAndView("register", "user", user);
    }
    else {
      return new ModelAndView("successRegister", "user", user);
    }
  }

  private User createUserAccount(User user) {
    return user;
  }

}
