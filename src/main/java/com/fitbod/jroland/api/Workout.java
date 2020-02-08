package com.fitbod.jroland.api;

import lombok.Getter;
import lombok.Setter;

public class Workout {

  @Getter
  @Setter
  private Long date;

  @Getter
  @Setter
  private Long duration;

  @Getter
  @Setter
  private Long workoutId;

}
