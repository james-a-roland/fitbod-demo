package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.Workout;
import com.fitbod.jroland.service.WorkoutService;
import com.fitbod.jroland.util.AuthenticationUtil;
import com.fitbod.jroland.util.ControllerUtil;
import com.fitbod.jroland.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class WorkoutController {

  @Autowired
  WorkoutService workoutService;

  @GetMapping(RouteUtil.WORKOUT)
  public String getWorkouts(Authentication authentication, Model model) {
    Optional<String> userMaybe = AuthenticationUtil.getUserEmail(authentication);
    if (userMaybe.isPresent()) {
      List<Workout> workouts = workoutService.getWorkoutsForEmail(userMaybe.get());
      ControllerUtil.writeRecordsToModel(workouts, model);
      ControllerUtil.writeUserToModel(userMaybe.get(), model);
    }
    ControllerUtil.writeEmptyObjectToModel(new Workout(), model);
    return RouteUtil.getTemplateForRoute(RouteUtil.WORKOUT);
  }

  @PostMapping(RouteUtil.WORKOUT)
  public String addWorkout(@ModelAttribute("workout") @Valid Workout workout,
                           BindingResult result, WebRequest request, Errors errors, Authentication authentication,
                           Model model) {
    Optional<String> userMaybe = AuthenticationUtil.getUserEmail(authentication);
    if (userMaybe.isPresent()) {
      workout.setEmailAddress(userMaybe.get());
      workoutService.createWorkout(workout);
    }
    return getWorkouts(authentication, model);
  }

}
