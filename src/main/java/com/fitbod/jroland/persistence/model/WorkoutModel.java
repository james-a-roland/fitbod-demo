package com.fitbod.jroland.persistence.model;

import com.fitbod.jroland.api.Workout;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class WorkoutModel {

  @Getter
  @Setter
  private String emailAddress;

  @Getter
  @Setter
  private long date;

  @Getter
  @Setter
  private long duration;

  public Workout toApi() {
    Workout workout = new Workout();
    workout.setEmailAddress(emailAddress);
    workout.setDate(new Date(date));
    workout.setDuration(duration);
    return workout;
  }

  public static WorkoutModel fromApi(Workout workout) {
    WorkoutModel workoutModel = new WorkoutModel();
    workoutModel.setEmailAddress(workout.getEmailAddress());
    workoutModel.setDate(workout.getDate().getTime());
    workoutModel.setDuration(workout.getDuration());
    return workoutModel;
  }

}
