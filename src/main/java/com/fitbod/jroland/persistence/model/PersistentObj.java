package com.fitbod.jroland.persistence.model;

import com.fitbod.jroland.api.ApiReadObject;
import com.fitbod.jroland.api.ApiWriteObject;

/**
 * An object to be stored in a database.
 */
public interface PersistentObj<R extends ApiReadObject, W extends ApiWriteObject> {

  /**
   * @return A presentable object for read operations by the client.
   */
  R toReadObject();
}
