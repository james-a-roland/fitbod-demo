package com.fitbod.jroland.persistence.repo;

import com.fitbod.jroland.api.workout.WorkoutWrite;
import com.fitbod.jroland.persistence.model.Workout;
import com.fitbod.jroland.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class WorkoutRepo extends RedisRepo<WorkoutWrite, Workout> {

  private static final String ID_TO_WORKOUT_KEY_PREFIX = "ID_TO_WORKOUT_KEY:";
  private static final String EMAIL_TO_WORKOUT_ID_KEY_PREFIX = "EMAIL_TO_WORKOUT_ID_KEY:";

  @Autowired
  public WorkoutRepo(JedisPool jedisPool) {
    super(jedisPool);
  }

  @Override
  protected List<Workout> multiReadImpl(List<String> keys, Jedis jedis) {
    List<String> idKeys = keys.stream().map(key -> ID_TO_WORKOUT_KEY_PREFIX + key).collect(Collectors.toList());
    String[] idKeyArray = idKeys.toArray(new String[idKeys.size()]);
    return jedis.mget(idKeyArray).stream()
            .filter(json -> !StringUtils.isEmpty(json))
            .map(json -> JsonUtil.fromJson(json, Workout.class))
            .collect(Collectors.toList());
  }

  @Override
  protected String upsertImpl(WorkoutWrite writeObj, Jedis jedis) {
    String assignedKey = Optional.ofNullable(writeObj.getKey()).orElse(UUID.randomUUID().toString());
    writeObj.setId(assignedKey);
    Workout workout = writeObj.toModel();

    String idKey = ID_TO_WORKOUT_KEY_PREFIX + assignedKey;
    String emailKey = EMAIL_TO_WORKOUT_ID_KEY_PREFIX + writeObj.getEmail();
    String json = JsonUtil.toJson(workout);
    Transaction atomic = jedis.multi();
    atomic.set(idKey, json);
    atomic.zadd(emailKey, writeObj.getDate().getTime(), writeObj.getId());
    List<Object> insertObj = atomic.exec();

    return assignedKey;
  }

  public List<Workout> findByEmail(String email, int start, int end) {
    String emailKey = EMAIL_TO_WORKOUT_ID_KEY_PREFIX + email;
    Jedis jedis = jedisPool.getResource();
    try {
      List<String> keys = new ArrayList<>(jedis.zrevrange(emailKey, start, end));
      return multiRead(keys);
    } finally {
      jedis.close();
    }
  }

  public long getTotal(String email) {
    String emailKey = EMAIL_TO_WORKOUT_ID_KEY_PREFIX + email;
    Jedis jedis = jedisPool.getResource();
    try {
      return jedis.zcount(emailKey, -Double.MAX_VALUE, Double.MAX_VALUE);
    } finally {
      jedis.close();
    }
  }

}
