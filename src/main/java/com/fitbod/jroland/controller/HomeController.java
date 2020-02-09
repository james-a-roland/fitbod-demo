package com.fitbod.jroland.controller;

import com.fitbod.jroland.util.AuthenticationUtil;
import com.fitbod.jroland.util.ControllerUtil;
import com.fitbod.jroland.util.RouteUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

  @GetMapping(RouteUtil.HOME)
  String getHome(Authentication authentication, Model model) {
    Optional<String> userMaybe = AuthenticationUtil.getUserEmail(authentication);
    if (userMaybe.isPresent()) {
      ControllerUtil.writeUserToModel(userMaybe.get(), model);
    }
    return RouteUtil.getTemplateForRoute(RouteUtil.HOME);
  }

}
