package com.fitbod.jroland.api.workout;

import com.fitbod.jroland.api.ApiReadObject;
import com.fitbod.jroland.persistence.model.Workout;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.Optional;

public class WorkoutRead extends ApiReadObject<Workout> {

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

  @Override
  public Optional<String> fetchReadError() {
    if (StringUtils.isEmpty(id)) {
      return Optional.of("Workout ID is required.");
    } else if (date == null) {
      return Optional.of("Date field is required.");

    } else if (duration == null || duration <= 0) {
      return Optional.of("Duration field MUST be positive.");
    }
    return Optional.empty();
  }

}
