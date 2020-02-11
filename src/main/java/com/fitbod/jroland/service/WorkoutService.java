package com.fitbod.jroland.service;

import com.fitbod.jroland.api.WorkoutApi;
import com.fitbod.jroland.persistence.model.Workout;
import com.fitbod.jroland.persistence.repo.RedisWorkoutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService extends PersistentService<WorkoutApi> {

  @Autowired
  RedisWorkoutRepo redisWorkoutRepo;

  @Override
  protected WorkoutApi createObject(WorkoutApi workoutApi) {
    Workout workout = Workout.fromApi(workoutApi);
    redisWorkoutRepo.upsert(Workout.fromApi(workoutApi));
    workoutApi.setId(workout.getId());
    return workoutApi;
  }

  @Override
  protected Optional<WorkoutApi> getObject(String key) {
    return redisWorkoutRepo.get(key).map(Workout::toApi);
  }

  public void delete(String key) {
    redisWorkoutRepo.delete(key);
  }

  public List<WorkoutApi> getWorkoutsForEmail(String email, int start, int end) {
    return redisWorkoutRepo.findByEmail(email, start, end)
            .stream()
            .map(Workout::toApi)
            .filter(workout -> !workout.fetchReadError().isPresent())
            .collect(Collectors.toList());
  }

  public long getTotalWorkoutsForEmail(String email) {
    return redisWorkoutRepo.getTotalWorkouts(email);
  }

}
