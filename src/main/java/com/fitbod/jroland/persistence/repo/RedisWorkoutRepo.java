package com.fitbod.jroland.persistence.repo;

import com.fitbod.jroland.persistence.model.Workout;
import com.fitbod.jroland.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RedisWorkoutRepo {

  private static final String ID_TO_WORKOUT_KEY_PREFIX = "ID_TO_WORKOUT_KEY:";
  private static final String EMAIL_TO_WORKOUTS_KEY_PREFIX = "EMAIL_TO_WORKOUT_KEY:";

  private final JedisPool jedisPool;

  @Autowired
  public RedisWorkoutRepo(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  public void upsert(Workout workout) {
    String idKey = ID_TO_WORKOUT_KEY_PREFIX + workout.getId();
    String emailKey = EMAIL_TO_WORKOUTS_KEY_PREFIX + workout.getEmail();
    String workoutJson = JsonUtil.toJson(workout);

    Jedis jedis = jedisPool.getResource();
    try {
      Transaction atomic = jedis.multi();
      atomic.set(idKey, workoutJson);
      atomic.zadd(emailKey, workout.getDate(), workoutJson);
      atomic.exec();
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
  }

  public Optional<Workout> get(String key) {
    String idKey = ID_TO_WORKOUT_KEY_PREFIX + key;
    Jedis jedis = jedisPool.getResource();
    try {
      return Optional.ofNullable(jedisPool.getResource().get(idKey))
              .map(json -> JsonUtil.fromJson(json, Workout.class));
    } finally {
      if (jedis != null) {
        jedis.close();
      }
      jedis.close();
    }
  }

  public void delete(String key) {
    String idKey = ID_TO_WORKOUT_KEY_PREFIX + key;
    String workoutJson = jedisPool.getResource().get(idKey);
    Workout workout = JsonUtil.fromJson(workoutJson, Workout.class);
    String emailKey = EMAIL_TO_WORKOUTS_KEY_PREFIX + workout.getEmail();

    Jedis jedis = jedisPool.getResource();
    try {
      Transaction atomic = jedis.multi();
      atomic.del(idKey);
      atomic.zrem(emailKey, workoutJson);
      atomic.exec();
    } finally {
      jedis.close();
    }
  }

  public List<Workout> findByEmail(String email, int start, int end) {
    String emailKey = EMAIL_TO_WORKOUTS_KEY_PREFIX + email;
    Jedis jedis = jedisPool.getResource();
    try {
      return jedis.zrevrange(emailKey, start, end).stream()
              .map(json -> JsonUtil.fromJson(json, Workout.class))
              .collect(Collectors.toList());
    } finally {
      jedis.close();
    }
  }

  public long getTotalWorkouts(String email) {
    String emailKey = EMAIL_TO_WORKOUTS_KEY_PREFIX + email;
    Jedis jedis = jedisPool.getResource();
    try {
      return jedis.zcount(emailKey, 0, Double.MAX_VALUE);
    } finally {
      jedis.close();
    }
  }

}
