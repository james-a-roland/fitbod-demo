package com.fitbod.jroland.service;

import com.fitbod.jroland.api.workout.WorkoutRead;
import com.fitbod.jroland.api.workout.WorkoutWrite;
import com.fitbod.jroland.persistence.model.Workout;
import com.fitbod.jroland.persistence.repo.WorkoutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService extends PersistentService<WorkoutRead, WorkoutWrite> {

  private final WorkoutRepo workoutRepo;

  @Autowired
  public WorkoutService(WorkoutRepo workoutRepo) {
    this.workoutRepo = workoutRepo;
  }

  @Override
  protected String upsertObject(WorkoutWrite toUpsert) {
    return workoutRepo.upsert(toUpsert);
  }

  @Override
  protected Optional<WorkoutRead> getObject(String key) {
    return workoutRepo.get(key).map(Workout::toReadObject).filter(WorkoutRead::canBeRead);
  }

  public List<WorkoutRead> getWorkoutsForEmail(String email, int start, int end) {
    return workoutRepo.findByEmail(email, start, end)
            .stream()
            .map(Workout::toReadObject)
            .filter(WorkoutRead::canBeRead)
            .collect(Collectors.toList());
  }

  public long getTotalWorkoutsForEmail(String email) {
    return workoutRepo.getTotal(email);
  }

}
