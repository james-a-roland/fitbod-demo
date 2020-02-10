package com.fitbod.jroland.service;

import com.fitbod.jroland.api.ApiObject;
import com.fitbod.jroland.exception.InvalidApiObjectException;

import java.util.Optional;

/**
 * A service containing business logic to perform some READ/WRITE operations
 * on an entity stored in an external DB.
 */
public abstract class PersistentService<OBJ extends ApiObject> {

  public final OBJ create(OBJ obj) {
    Optional<String> writeErr = obj.fetchWriteError();
    if (writeErr.isPresent()) {
      throw new InvalidApiObjectException(writeErr.get());
    }
    return createObject(obj);
  }

  public final Optional<OBJ> get(String key) {
    Optional<OBJ> obj = getObject(key);
    Optional<String> readErr = obj.flatMap(ApiObject::fetchReadError);
    if (readErr.isPresent()) {
      if (readErr.isPresent()) {
        throw new InvalidApiObjectException(readErr.get());
      }
    }
    return obj;
  }

  /**
   * Service level logic to create an object that has been validated.
   */
  protected abstract OBJ createObject(OBJ obj);

  /**
   * Service level logic to get an object to be validated.
   */
  protected abstract Optional<OBJ> getObject(String key);
}
