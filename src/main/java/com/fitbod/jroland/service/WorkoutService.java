package com.fitbod.jroland.service;

import com.fitbod.jroland.api.Workout;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

  public List<Workout> getWorkoutsForUser(String username) {
    return ImmutableList.of();
  }

  public Workout createWorkoutForUser(String username, Workout workout) {
    return new Workout();
  }

}
