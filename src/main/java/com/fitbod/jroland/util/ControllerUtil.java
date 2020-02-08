package com.fitbod.jroland.util;

import org.springframework.ui.Model;

public class ControllerUtil {

  private static final String USERNAME = "username";

  public static void writeUserToModel(String username, Model model) {
    model.addAttribute(USERNAME, username);
  }

}
