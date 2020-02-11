package com.fitbod.jroland.controller;

import com.fitbod.jroland.api.WorkoutApi;
import com.fitbod.jroland.service.WorkoutService;
import com.fitbod.jroland.util.AuthenticationUtil;
import com.fitbod.jroland.util.ControllerUtil;
import com.fitbod.jroland.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class WorkoutController {

  @Autowired
  WorkoutService workoutService;

  /**
   * Get all workouts for the logged in user.
   *
   * @param start The start index of the element to retrieve (inclusive).
   * @param end   The end index of the element to retrieve (inclusive).
   */
  @GetMapping(value = RouteUtil.WORKOUT)
  public String getWorkouts(@RequestParam(value = "start", required = false, defaultValue = "") Optional<Integer> start,
                            @RequestParam(value = "end", required = false, defaultValue = "") Optional<Integer> end,
                            Authentication authentication,
                            Model model) {
    Optional<String> emailMaybe = AuthenticationUtil.getUserEmail(authentication);
    if (emailMaybe.isPresent()) {
      List<WorkoutApi> workoutApis = workoutService.getWorkoutsForEmail(emailMaybe.get(),
                                                                        start.orElse(0),
                                                                        end.orElse(9));
      Long numWorkouts = workoutService.getTotalWorkoutsForEmail(emailMaybe.get());

      ControllerUtil.writeRecordsToModel(workoutApis, model);
      ControllerUtil.writeUserToModel(emailMaybe.get(), model);
      model.addAttribute("numWorkouts", numWorkouts);
    }
    ControllerUtil.writeEmptyObjectToModel(new WorkoutApi(), model);
    return RouteUtil.getTemplateForRoute(RouteUtil.WORKOUT);
  }

  @PostMapping(RouteUtil.WORKOUT)
  public String addWorkout(@ModelAttribute("workout") @Valid WorkoutApi workoutApi,
                           Authentication authentication,
                           Model model) {
    Optional<String> userMaybe = AuthenticationUtil.getUserEmail(authentication);
    if (userMaybe.isPresent()) {
      workoutApi.setEmail(userMaybe.get());
      workoutService.create(workoutApi);
    }
    return getWorkouts(Optional.empty(), Optional.empty(), authentication, model);
  }

}
