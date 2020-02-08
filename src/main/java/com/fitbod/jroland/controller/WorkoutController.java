package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.Workout;
import com.fitbod.jroland.service.WorkoutService;
import com.fitbod.jroland.util.AuthenticationUtil;
import com.fitbod.jroland.util.ControllerUtil;
import com.fitbod.jroland.util.RouteConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class WorkoutController {

  @Autowired
  WorkoutService workoutService;

  @GetMapping(RouteConstants.WORKOUT)
  public String getWorkouts(Authentication authentication, Model model) {
    Optional<String> userMaybe = AuthenticationUtil.getUser(authentication);
    if (userMaybe.isPresent()) {
      List<Workout> workouts = workoutService.getWorkoutsForUser(userMaybe.get());
      model.addAttribute("entry", workouts);
    }
    return "workout";
  }

  @PostMapping(RouteConstants.WORKOUT)
  public String addWorkout(Authentication authentication, Model model, Workout workout) {
    Optional<String> userMaybe = AuthenticationUtil.getUser(authentication);
    if (userMaybe.isPresent()) {
      workoutService.createWorkoutForUser(userMaybe.get(), workout);
      List<Workout> workouts = workoutService.getWorkoutsForUser(userMaybe.get());
      ControllerUtil.writeUserToModel(userMaybe.get(), model);
    }
    return "workout";
  }

}
