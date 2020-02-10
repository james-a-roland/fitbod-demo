package com.fitbod.jroland.util;

import com.fitbod.jroland.api.ApiObject;
import org.springframework.ui.Model;

import java.util.Collection;

public class ControllerUtil {

  private static final String USER_KEY = "username";
  private static final String RECORDS_KEY = "records";
  private static final String EMPTY_OBJECT_KEY = "newObject";

  public static void writeUserToModel(String username, Model model) {
    model.addAttribute(USER_KEY, username);
  }

  public static void writeRecordsToModel(Collection records, Model model) {
    model.addAttribute(RECORDS_KEY, records);
  }

  public static void writeEmptyObjectToModel(ApiObject apiObject, Model model) {
    model.addAttribute(EMPTY_OBJECT_KEY, apiObject);
  }

}
