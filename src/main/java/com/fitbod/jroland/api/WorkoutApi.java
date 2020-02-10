package com.fitbod.jroland.api;

import com.fitbod.jroland.persistence.model.Workout;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.Optional;

public class WorkoutApi implements ApiObject<Workout> {

  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date date;

  @Getter
  @Setter
  private Long duration;


  @Override
  public Optional<String> fetchWriteError() {
    if (!StringUtils.isEmpty(id)) {
      return Optional.of("Clients cannot assign ID upon write.");
    }
    return checkNonIdFieldsValid();
  }

  @Override
  public Optional<String> fetchReadError() {
    if (StringUtils.isEmpty(id)) {
      return Optional.of("Clients need ID for read operations.");
    }
    return checkNonIdFieldsValid();
  }

  private Optional<String> checkNonIdFieldsValid() {
    if (StringUtils.isEmpty(email)) {
      return Optional.of("Email address is required");
    } else if (date == null) {
      return Optional.of("Date field is required.");
    } else if (duration == null) {
      return Optional.of("Duration field is required.");
    } else if (duration <= 0) {
      return Optional.of("Duration field MUST be positive.");
    }
    return Optional.empty();
  }
}
