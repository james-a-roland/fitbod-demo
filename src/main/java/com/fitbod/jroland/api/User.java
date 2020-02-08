package com.fitbod.jroland.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class User {

  @Getter
  @Setter
  @NotNull
  @NotBlank
  private String email;

  @Getter
  @Setter
  @NotNull
  @NotBlank
  private String password;

}
