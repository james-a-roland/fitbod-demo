package com.fitbod.jroland.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class RouteUtil {

  //User facing routes.
  public static final String HOME = "/";
  public static final String REGISTER = "/register";
  public static final String WORKOUT = "/workout";
  public static final String LOGIN = "/login";

  //API paths.
  public static final String API_USER = "/api/user";

  private static final Map<String, String> ROUTE_TO_TEMPLATE_MAP = new ImmutableMap.Builder<String, String>()
          .put(HOME, "index")
          .put(REGISTER, "register")
          .put(WORKOUT, "workout")
          .put(LOGIN, "login")
          .build();

  public static final String getTemplateForRoute(String route) {
    return ROUTE_TO_TEMPLATE_MAP.get(route);
  }

}
