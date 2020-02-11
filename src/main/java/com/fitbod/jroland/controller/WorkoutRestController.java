package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.WorkoutApi;
import com.fitbod.jroland.service.WorkoutService;
import com.fitbod.jroland.util.AuthenticationUtil;
import com.fitbod.jroland.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
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
   * Create a workout for the existing user.
   */
  @PostMapping
  public ResponseEntity<Void> create(@RequestBody WorkoutApi workoutApi, Authentication authentication,
                                     UriComponentsBuilder componentsBuilder) {
    if (!AuthenticationUtil.isAuthenticated(authentication)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    workoutApi.setEmail(AuthenticationUtil.getUserEmail(authentication).get());
    WorkoutApi createdWorkout = workoutService.create(workoutApi);
    String createdId = createdWorkout.getId();
    return ResponseEntity.created(componentsBuilder.path(RouteUtil.API_WORKOUT + "/{createdId}").buildAndExpand(createdId).toUri()).build();
  }

  /**
   * Find workouts associated with currently logged in user.
   * @param start The start index of the element to retrieve (inclusive).
   * @param end The end index of the element to retrieve (inclusive).
   */
  @GetMapping
  public ResponseEntity<Collection<WorkoutApi>> getWorkouts(Authentication authentication,
                                                            @RequestParam(defaultValue = "0") int start,
                                                            @RequestParam(defaultValue = "9") int end) {
    if (!AuthenticationUtil.isAuthenticated(authentication)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    String email = AuthenticationUtil.getUserEmail(authentication).get();
    List<WorkoutApi> workouts = workoutService.getWorkoutsForEmail(email, start, end);
    return ResponseEntity.ok(workouts);
  }

  /**
   * Get a specific workout by id.
   */
  @GetMapping(path = "/{workoutId}")
  public ResponseEntity<WorkoutApi> getWorkout(Authentication authentication, @PathVariable String workoutId) {
    Optional<WorkoutApi> workout = workoutService.get(workoutId);
    return ResponseEntity.of(workout);
  }

  /**
   * Delete a workout for a given user.
   */
  @DeleteMapping(path = "/{workoutId}")
  public ResponseEntity<Void> deleteWorkout(Authentication authentication, @PathVariable String workoutId) {
    if (!AuthenticationUtil.isAuthenticated(authentication)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    workoutService.delete(workoutId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
