package com.fitbod.jroland.service;

import com.fitbod.jroland.api.workout.WorkoutRead;
import com.fitbod.jroland.api.workout.WorkoutWrite;
import com.fitbod.jroland.exception.InvalidApiObjectException;
import com.fitbod.jroland.persistence.repo.WorkoutRepo;
import com.github.fppt.jedismock.RedisServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestWorkoutService {


  private RedisServer redisServer;
  private WorkoutService workoutService;

  @Before
  public void setup() throws IOException {
    redisServer = RedisServer.newRedisServer();
    redisServer.start();

    JedisPool jedisPool = new JedisPool(redisServer.getHost(), redisServer.getBindPort());
    WorkoutRepo workoutRepo = new WorkoutRepo(jedisPool);
    workoutService = new WorkoutService(workoutRepo);
  }

  @Test
  public void testUpsertAndGetNoId() {
    WorkoutWrite write = getFullWorkoutWrite();
    write.setId(null);

    String id = workoutService.upsert(write);
    Assert.assertFalse(StringUtils.isEmpty(id));
    Optional<WorkoutRead> readMaybe = workoutService.get(id);
    Assert.assertTrue(readMaybe.isPresent());

    WorkoutRead read = readMaybe.get();
    Assert.assertEquals(id, read.getId());
    Assert.assertEquals(write.getDuration(), read.getDuration());
    Assert.assertEquals(write.getDate(), read.getDate());
  }

  @Test
  public void testUpsertAndGetWithId() {
    WorkoutWrite write = getFullWorkoutWrite();
    Optional<WorkoutRead> emptyRead = workoutService.get(write.getKey());
    Assert.assertFalse(emptyRead.isPresent());

    String id = workoutService.upsert(write);
    Assert.assertFalse(StringUtils.isEmpty(id));
    Assert.assertEquals(id, write.getId());
    Optional<WorkoutRead> readMaybe = workoutService.get(id);
    Assert.assertTrue(readMaybe.isPresent());

    WorkoutRead read = readMaybe.get();
    Assert.assertEquals(id, read.getId());
    Assert.assertEquals(write.getDuration(), read.getDuration());
    Assert.assertEquals(write.getDate(), read.getDate());
  }

  @Test
  public void testUpdate() {
    Long originalDuration = 1L;
    WorkoutWrite originalWrite = getFullWorkoutWrite();
    originalWrite.setDuration(originalDuration);
    String id = workoutService.upsert(originalWrite);
    WorkoutRead originalRead = workoutService.get(id).get();

    Long updateDuration = 1000L;
    WorkoutWrite updateWrite = getFullWorkoutWrite();
    updateWrite.setId(id);
    updateWrite.setDuration(updateDuration);
    workoutService.upsert(updateWrite);
    WorkoutRead updateRead = workoutService.get(id).get();

    Assert.assertEquals(originalRead.getId(), updateRead.getId());
    Assert.assertEquals(originalRead.getDate(), updateRead.getDate());
    Assert.assertEquals(originalRead.getDuration(), originalDuration);
    Assert.assertEquals(updateRead.getDuration(), updateDuration);
  }

  /**
   * The mock Jedis server does not support these operations out of the box,
   * so these tests are disabled.
   */
  @Ignore
  @Test()
  public void testGetEmailsForUser() {
    String myEmail = "email1@fittestbod.com";
    WorkoutWrite write1 = getFullWorkoutWrite();
    workoutService.upsert(write1);

    int numWorkouts = 5;
    String theirEmail = "email2@fitterestbod.com";
    List<WorkoutWrite> theirWorkoutsWrites = new ArrayList<>();
    for (int i = 0; i < numWorkouts; i++) {
      WorkoutWrite secondEmailWrite = getFullWorkoutWrite();
      secondEmailWrite.setEmail(theirEmail);
      workoutService.upsert(secondEmailWrite);
    }

    List<WorkoutRead> myWorkouts = workoutService.getWorkoutsForEmail(myEmail, 0, 2);
    List<WorkoutRead> theirWorkouts = workoutService.getWorkoutsForEmail(myEmail, 0, 2);
    Assert.assertEquals(myWorkouts.size(), 1);
    Assert.assertEquals(theirWorkouts.size(), 3);
  }
  @Test(expected = InvalidApiObjectException.class)
  public void testInvalidWorkoutWrite() {
    WorkoutWrite write = new WorkoutWrite();
    write.setEmail("Hello");
    workoutService.upsert(write);
  }

  @After
  public void after() {
    redisServer.stop();
  }

  private static final WorkoutWrite getFullWorkoutWrite() {
    WorkoutWrite fullWorkout = new WorkoutWrite();
    fullWorkout.setEmail("validEmail@gmail.com");
    fullWorkout.setId(UUID.randomUUID().toString());
    fullWorkout.setDate(new Date(5000L));
    fullWorkout.setDuration(100L);
    return fullWorkout;
  }
}
