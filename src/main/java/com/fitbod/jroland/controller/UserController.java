package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.User;
import com.fitbod.jroland.service.UserService;
import com.fitbod.jroland.util.ControllerUtil;
import com.fitbod.jroland.util.RouteUtil;
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

import javax.validation.Valid;

@Controller
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping(RouteUtil.REGISTER)
  public String showRegistrationForm(Model model) {
    ControllerUtil.writeEmptyObjectToModel(new User(), model);
    return RouteUtil.getTemplateForRoute(RouteUtil.REGISTER);
  }

  @PostMapping(RouteUtil.REGISTER)
  public String registerUserAccount(@ModelAttribute("user") @Valid User user, BindingResult result, Errors errors,
                                    Model model) {
    //Encrypt the password ASAP for application use.
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    if (!result.hasErrors()) {
      userService.registerNewUser(user);
      ControllerUtil.writeUserToModel(user.getEmail(), model);
    }
    return showRegistrationForm(model);
  }

}
