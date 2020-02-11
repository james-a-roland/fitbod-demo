package com.fitbod.jroland.controller.workout;

import com.fitbod.jroland.api.workout.WorkoutRead;
import com.fitbod.jroland.api.workout.WorkoutWrite;
import com.fitbod.jroland.service.WorkoutService;
import com.fitbod.jroland.util.AuthenticationUtil;
import com.fitbod.jroland.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RouteUtil.API_WORKOUT)
public class WorkoutRestController {

  @Autowired
  WorkoutService workoutService;

  /**
   * Create or update a workout for the existing user.
   */
  @PostMapping
  public ResponseEntity<Void> upsert(@RequestBody WorkoutWrite workoutWrite, Authentication authentication,
                                     UriComponentsBuilder componentsBuilder) {
    if (!AuthenticationUtil.isAuthenticated(authentication)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    workoutWrite.setEmail(AuthenticationUtil.getUserEmail(authentication).get());
    String createdId = workoutService.upsert(workoutWrite);
    return ResponseEntity.created(componentsBuilder.path(RouteUtil.API_WORKOUT + "/{createdId}").buildAndExpand(
            createdId).toUri()).build();
  }

  /**
   * Find workouts associated with currently logged in user.
   *
   * @param start The start index of the element to retrieve (inclusive).
   * @param end   The end index of the element to retrieve (inclusive).
   */
  @GetMapping
  public ResponseEntity<Collection<WorkoutRead>> getWorkouts(Authentication authentication,
                                                             @RequestParam(defaultValue = "0") int start,
                                                             @RequestParam(defaultValue = "9") int end) {
    if (!AuthenticationUtil.isAuthenticated(authentication)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    String email = AuthenticationUtil.getUserEmail(authentication).get();
    List<WorkoutRead> workouts = workoutService.getWorkoutsForEmail(email, start, end);
    return ResponseEntity.ok(workouts);
  }

  /**
   * Get a specific workout by id.
   */
  @GetMapping(path = "/{workoutId}")
  public ResponseEntity<WorkoutRead> getWorkout(Authentication authentication, @PathVariable String workoutId) {
    Optional<WorkoutRead> workout = workoutService.get(workoutId);
    return ResponseEntity.of(workout);
  }

}
