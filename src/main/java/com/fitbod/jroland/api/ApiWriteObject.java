package com.fitbod.jroland.api;

import com.fitbod.jroland.persistence.model.PersistentObj;

import java.util.Optional;

/**
 * An object representing a write use case to the application.
 *
 * @param <M> The underlying database associated model object.
 */
public abstract class ApiWriteObject<M extends PersistentObj> {

  /**
   * @return An Optional that contains an error describing why the API object cannot be written safely
   * to a data store or empty otherwise.
   */
  public abstract Optional<String> fetchWriteError();

  /**
   * @return A {@link PersistentObj} that will be the resultant transformation of the write use case.
   */
  public abstract M toModel();

  public abstract String getKey();

  public final boolean canBeWritten() {
    return !fetchWriteError().isPresent();
  }
}
