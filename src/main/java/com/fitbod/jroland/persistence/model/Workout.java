package com.fitbod.jroland.persistence.model;

import com.fitbod.jroland.api.WorkoutApi;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

public class Workout implements PersistentObj {

  @Getter
  private String id;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private long date;

  @Getter
  @Setter
  private long duration;

  public WorkoutApi toApi() {
    WorkoutApi workoutApi = new WorkoutApi();
    workoutApi.setId(id);
    workoutApi.setEmail(email);
    workoutApi.setDate(new Date(date));
    workoutApi.setDuration(duration);
    return workoutApi;
  }

  public static Workout fromApi(WorkoutApi workoutApi) {
    Workout workout = new Workout();
    workout.id = UUID.randomUUID().toString();
    workout.setEmail(workoutApi.getEmail());
    workout.setDate(workoutApi.getDate().getTime());
    workout.setDuration(workoutApi.getDuration());
    return workout;
  }

}
