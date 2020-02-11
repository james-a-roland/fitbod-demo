package com.fitbod.jroland.api.workout;

import com.fitbod.jroland.api.ApiWriteObject;
import com.fitbod.jroland.persistence.model.Workout;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.Optional;

public class WorkoutWrite extends ApiWriteObject<Workout> {

  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date date;

  @Getter
  @Setter
  private Long duration;

  @Getter
  @Setter
  private String email;

  @Override
  public Optional<String> fetchWriteError() {
    if (StringUtils.isEmpty(email)) {
      return Optional.of("Email is required");
    } else if (date == null) {
      return Optional.of("Date field is required.");
    } else if (duration == null || duration <= 0) {
      return Optional.of("Duration field MUST be positive.");
    }
    return Optional.empty();
  }

  @Override
  public Workout toModel() {
    Workout workout = new Workout();
    workout.setId(id);
    workout.setDate(date.getTime());
    workout.setDuration(duration);
    return workout;
  }

  @Override
  public String getKey() {
    return id;
  }
}
