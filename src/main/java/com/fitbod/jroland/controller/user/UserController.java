package com.fitbod.jroland.controller.user;

import com.fitbod.jroland.api.user.UserWrite;
import com.fitbod.jroland.service.UserService;
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
    model.addAttribute("object", new UserWrite());
    return RouteUtil.getTemplateForRoute(RouteUtil.REGISTER);
  }

  @PostMapping(RouteUtil.REGISTER)
  public String registerUserAccount(@ModelAttribute("user") UserWrite user, Model model) {
    userService.upsert(user);
    model.addAttribute("user", user.getEmail());
    return showRegistrationForm(model);
  }

}
