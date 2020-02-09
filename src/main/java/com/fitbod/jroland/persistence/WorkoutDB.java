package com.fitbod.jroland.persistence;

import com.fitbod.jroland.api.Workout;
import com.fitbod.jroland.persistence.model.WorkoutModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkoutDB {


  private Map<String, List<WorkoutModel>> workouts = new HashMap<>();

  public Workout createWorkout(Workout workout) {
    List<WorkoutModel> existingWorkouts = workouts.getOrDefault(workout.getEmailAddress(), new ArrayList<>());
    WorkoutModel workoutModel = WorkoutModel.fromApi(workout);
    existingWorkouts.add(workoutModel);
    workouts.put(workout.getEmailAddress(), existingWorkouts);

    return workoutModel.toApi();
  }

  public List<Workout> getWorkoutsForEmailAddress(String emailAddress, int start, int count) {
    return workouts.getOrDefault(emailAddress, new ArrayList<>()).stream()
        .skip(start)
        .limit(count)
        .map(WorkoutModel::toApi)
        .collect(Collectors.toList());
  }

}
