package com.fitbod.jroland.api;

import com.fitbod.jroland.persistence.model.PersistentObj;

import java.util.Optional;

/**
 * An object obtained from or displayed to an external client.
 * Note: Methods cannot start with the word "get" or Spring will automatically
 * populate these into the JSON response.
 * @param <M> The underlying database associated model object.
 */
public interface ApiObject<M extends PersistentObj> {

  /**
   * @return An Optional that contains an error describing why the API object cannot be written safely
   * to a data store or empty otherwise.
   */
  Optional<String> fetchWriteError();

  /**
   * @return An Optional that contains an error describing why the Model object cannot be read by a client
   * or empty otherwise.
   */
  Optional<String> fetchReadError();

}
