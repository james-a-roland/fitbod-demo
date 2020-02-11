package com.fitbod.jroland.util;

import com.fitbod.jroland.api.ApiWriteObject;
import org.springframework.ui.Model;

public class ControllerUtil {

  private static final String USER_KEY = "username";
  private static final String EMPTY_OBJECT_KEY = "newObject";

  public static void writeUserToModel(String username, Model model) {
    model.addAttribute(USER_KEY, username);
  }

  public static void writeEmptyObjectToModel(ApiWriteObject emptyWriteObject, Model model) {
    model.addAttribute(EMPTY_OBJECT_KEY, emptyWriteObject);
  }

}
