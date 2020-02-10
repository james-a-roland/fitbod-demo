package com.fitbod.jroland.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static String toJson(Object object) {
    try {
      return MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Object cannot be serialized to json: " + object);
    }
  }

  public static <T> T fromJson(String json, Class<T> objClass) {
    try {
      return MAPPER.readValue(json, objClass);
    } catch (IOException e) {
      throw new IllegalArgumentException("JSON " + json + " cannot be serialized to object class: " + objClass);
    }
  }
}
