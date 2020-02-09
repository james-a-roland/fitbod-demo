package com.fitbod.jroland.service;

import com.fitbod.jroland.api.Workout;
import com.fitbod.jroland.persistence.WorkoutDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

  @Autowired
  WorkoutDB workoutDB;

  public List<Workout> getWorkoutsForEmail(String emailAdddress) {
    return workoutDB.getWorkoutsForEmailAddress(emailAdddress, 0, 10);
  }

  public Workout createWorkout(Workout workout) {
    return workoutDB.createWorkout(workout);
  }

}
