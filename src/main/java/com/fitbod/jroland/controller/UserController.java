package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.UserApi;
import com.fitbod.jroland.service.UserService;
import com.fitbod.jroland.util.ControllerUtil;
import com.fitbod.jroland.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping(RouteUtil.REGISTER)
  public String showRegistrationForm(Model model) {
    ControllerUtil.writeEmptyObjectToModel(new UserApi(), model);
    return RouteUtil.getTemplateForRoute(RouteUtil.REGISTER);
  }

  @PostMapping(RouteUtil.REGISTER)
  public String registerUserAccount(@ModelAttribute("user") UserApi user, Model model) {
    userService.create(user);
    ControllerUtil.writeUserToModel(user.getEmail(), model);
    return showRegistrationForm(model);
  }

}
