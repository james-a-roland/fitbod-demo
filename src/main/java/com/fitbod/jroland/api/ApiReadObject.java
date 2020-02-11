package com.fitbod.jroland.api;

import com.fitbod.jroland.persistence.model.PersistentObj;

import java.util.Optional;

/**
 * An object representing a read use case from the application.
 *
 * @param <M> The underlying database associated model object.
 */
public abstract class ApiReadObject<M extends PersistentObj> {

  /**
   * @return An Optional that contains an error describing why the Model object cannot be read by a client
   * or empty otherwise.
   */
  public abstract Optional<String> fetchReadError();

  public final boolean canBeRead() {
    return !fetchReadError().isPresent();
  }
}
