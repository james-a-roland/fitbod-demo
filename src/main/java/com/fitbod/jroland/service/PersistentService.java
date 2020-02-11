package com.fitbod.jroland.service;

import com.fitbod.jroland.api.ApiReadObject;
import com.fitbod.jroland.api.ApiWriteObject;
import com.fitbod.jroland.exception.InvalidApiObjectException;

import java.util.Optional;

/**
 * A service containing business logic to perform some READ/WRITE operations
 * on an entity stored in an external DB.
 */
public abstract class PersistentService<READ extends ApiReadObject, WRITE extends ApiWriteObject> {

  public final String upsert(WRITE toUpsert) {
    Optional<String> writeErr = toUpsert.fetchWriteError();
    if (writeErr.isPresent()) {
      throw new InvalidApiObjectException(writeErr.get());
    }
    return upsertObject(toUpsert);
  }

  public final Optional<READ> get(String key) {
    Optional<READ> obj = getObject(key);
    Optional<String> readErr = obj.flatMap(ApiReadObject::fetchReadError);
    if (readErr.isPresent()) {
      throw new InvalidApiObjectException(readErr.get());
    }
    return obj;
  }

  /**
   * Service level logic to upsert an object that has been validated.
   * @return A string representing the key.
   */
  protected abstract String upsertObject(WRITE toUpsert);

  /**
   * Service level logic to get an object to be validated.
   */
  protected abstract Optional<READ> getObject(String key);
}
