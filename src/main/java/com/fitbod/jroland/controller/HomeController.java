package com.fitbod.jroland.controller;

import com.fitbod.jroland.util.AuthenticationUtil;
import com.fitbod.jroland.util.ControllerUtil;
import com.fitbod.jroland.util.RouteConstants;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

  @GetMapping(RouteConstants.HOME)
  String getHome(Authentication authentication, Model model) {
    Optional<String> userMaybe = AuthenticationUtil.getUser(authentication);
    if (userMaybe.isPresent()) {
      ControllerUtil.writeUserToModel(userMaybe.get(), model);
    }
    return "index";
  }

}
