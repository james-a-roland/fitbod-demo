package com.fitbod.jroland.persistence.repo;

import com.fitbod.jroland.api.ApiWriteObject;
import com.fitbod.jroland.persistence.model.PersistentObj;
import com.google.common.collect.ImmutableList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Provides safe access to an injected {@link JedisPool} for database access.
 */
public abstract class RedisRepo<W extends ApiWriteObject, P extends PersistentObj> {

  protected JedisPool jedisPool;

  public RedisRepo(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  /**
   * Return a list of all of the keys that were found in the Redis instance.
   */
  public final List<P> multiRead(List<String> keys) {
    if (keys.size() == 0) {
      return ImmutableList.of();
    }

    Jedis jedis = jedisPool.getResource();
    try {
      return multiReadImpl(keys, jedis).stream()
              .filter(Objects::nonNull)
              .collect(Collectors.toList());
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
  }


  /**
   * Create or update a model existing at some key.
   */
  public final String upsert(W writeObj) {
    Jedis jedis = jedisPool.getResource();
    try {
      return upsertImpl(writeObj, jedis);
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
  }

  /**
   * Single get operation given a key.
   */
  public Optional<P> get(String key) {
    return multiRead(ImmutableList.of(key)).stream().findFirst();
  }

  /**
   * Custom implementation of a multi read operation.
   */
  protected abstract List<P> multiReadImpl(List<String> key, Jedis jedis);


  /**
   * Custom implementation for an upsert operation.
   */
  protected abstract String upsertImpl(W writeObj, Jedis jedis);

}
