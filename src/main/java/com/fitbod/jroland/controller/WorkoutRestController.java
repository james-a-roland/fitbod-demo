package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.Workout;
import com.fitbod.jroland.service.WorkoutService;
import com.fitbod.jroland.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/workout")
public class WorkoutRestController {

  @Autowired
  WorkoutService workoutService;

  /**
   * Create a workout for the existing user.
   */
  @PostMapping
  public ResponseEntity<Void> create(@RequestBody Workout workout, UriComponentsBuilder componentsBuilder,
                                     Authentication authentication) {
    if (!AuthenticationUtil.isAuthenticated(authentication)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    workoutService.createWorkout(workout);
    return ResponseEntity.ok().build();
  }

  /**
   * Find workouts associated with currently logged in user.
   */
  @GetMapping
  public ResponseEntity<Collection<Workout>> getWorkouts(Authentication authentication) {
    if (!AuthenticationUtil.isAuthenticated(authentication)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    Optional<Collection<Workout>> workouts = AuthenticationUtil.getUserEmail(authentication)
            .map(workoutService::getWorkoutsForEmail);
    return ResponseEntity.of(workouts);
  }

  /**
   * Delete a workout for a given user.
   */
  @DeleteMapping
  public ResponseEntity<Void> deleteWorkout(Authentication authentication) {
    if (!AuthenticationUtil.isAuthenticated(authentication)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }


}
