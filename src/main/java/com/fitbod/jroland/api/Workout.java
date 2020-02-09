package com.fitbod.jroland.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Workout implements APIObject {

  @Getter
  @Setter
  private String emailAddress;

  @Getter
  @Setter
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date date;

  @Getter
  @Setter
  private Long duration;

}
