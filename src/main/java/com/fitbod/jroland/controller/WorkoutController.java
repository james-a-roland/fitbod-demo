package com.fitbod.jroland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WorkoutController {

  @GetMapping("/workouts")
  public String getWorkouts() {
    return "error";
  }

  @PostMapping("/workouts")
  public String addWorkout() {
    return "error";
  }


}
