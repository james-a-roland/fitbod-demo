package com.fitbod.jroland.controller.workout;

import com.fitbod.jroland.api.workout.WorkoutRead;
import com.fitbod.jroland.api.workout.WorkoutWrite;
import com.fitbod.jroland.service.WorkoutService;
import com.fitbod.jroland.util.AuthenticationUtil;
import com.fitbod.jroland.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    String email = AuthenticationUtil.getUserEmailUnsafe(authentication);
    List<WorkoutRead> workoutReads = workoutService.getWorkoutsForEmail(email,
                                                                        start.orElse(0),
                                                                        end.orElse(9));
    Long numWorkouts = workoutService.getTotalWorkoutsForEmail(email);

    model.addAttribute("records", workoutReads);
    model.addAttribute("numWorkouts", numWorkouts);
    model.addAttribute("object", new WorkoutWrite());
    return RouteUtil.getTemplateForRoute(RouteUtil.WORKOUT);
  }

  /**
   * Get a single workout for the logged in user.
   */
  @GetMapping(value = RouteUtil.WORKOUT + "/{id}")
  public String getWorkout(@PathVariable String id,
                           Authentication authentication,
                           Model model) {
    AuthenticationUtil.verifyAuthenticated(authentication);
    WorkoutRead workout = workoutService.get(id).orElse(new WorkoutRead());
    model.addAttribute("object", workout);
    return "workout_single";
  }

  @PostMapping(RouteUtil.WORKOUT)
  public String addWorkout(@ModelAttribute("workout") @Valid WorkoutWrite workoutWrite,
                           Authentication authentication,
                           Model model) {
    String email = AuthenticationUtil.getUserEmailUnsafe(authentication);
    workoutWrite.setEmail(email);
    workoutService.upsert(workoutWrite);
    return getWorkouts(Optional.empty(), Optional.empty(), authentication, model);
  }

}
