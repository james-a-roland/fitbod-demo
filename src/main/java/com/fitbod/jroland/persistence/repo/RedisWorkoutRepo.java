package com.fitbod.jroland.persistence.repo;

import com.fitbod.jroland.persistence.model.Workout;
import com.fitbod.jroland.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RedisWorkoutRepo {

  private static final String ID_TO_WORKOUT_KEY_PREFIX = "ID_TO_WORKOUT_KEY:";
  private static final String EMAIL_TO_WORKOUTS_KEY_PREFIX = "EMAIL_TO_WORKOUT_KEY:";

  private final Jedis jedis;

  @Autowired
  public RedisWorkoutRepo(Jedis jedis) {
    this.jedis = jedis;
  }

  public void upsert(Workout workout) {
    String idKey = ID_TO_WORKOUT_KEY_PREFIX + workout.getId();
    String emailKey = EMAIL_TO_WORKOUTS_KEY_PREFIX + workout.getEmail();
    String workoutJson = JsonUtil.toJson(workout);

    Transaction atomic = jedis.multi();
    atomic.set(idKey, workoutJson);
    atomic.zadd(emailKey, workout.getDate(), workoutJson);
    atomic.exec();
  }

  public Optional<Workout> get(String key) {
    String idKey = ID_TO_WORKOUT_KEY_PREFIX + key;
    return Optional.ofNullable(jedis.get(idKey))
            .map(json -> JsonUtil.fromJson(json, Workout.class));
  }

  public void delete(String key) {
    String idKey = ID_TO_WORKOUT_KEY_PREFIX + key;
    String workoutJson = jedis.get(idKey);
    Workout workout = JsonUtil.fromJson(workoutJson, Workout.class);
    String emailKey = EMAIL_TO_WORKOUTS_KEY_PREFIX + workout.getEmail();

    Transaction atomic = jedis.multi();
    atomic.del(idKey);
    atomic.zrem(emailKey, workoutJson);
    atomic.exec();
  }

  public List<Workout> findByEmail(String email, int start, int count) {
    String emailKey = EMAIL_TO_WORKOUTS_KEY_PREFIX + email;
    return jedis.zrevrange(emailKey, start, count).stream()
            .map(json -> JsonUtil.fromJson(json, Workout.class))
            .collect(Collectors.toList());
  }

}