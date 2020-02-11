package com.fitbod.jroland.persistence.model;

import com.fitbod.jroland.api.workout.WorkoutRead;
import com.fitbod.jroland.api.workout.WorkoutWrite;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Workout implements PersistentObj<WorkoutRead, WorkoutWrite> {

  @Setter
  @Getter
  private String id;

  @Getter
  @Setter
  private long date;

  @Getter
  @Setter
  private long duration;

  @Override
  public WorkoutRead toReadObject() {
    WorkoutRead read = new WorkoutRead();
    read.setId(id);
    read.setDate(new Date(date));
    read.setDuration(duration);
    return read;
  }

}
