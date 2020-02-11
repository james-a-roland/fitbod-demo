package com.fitbod.jroland.api.workout;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(DataProviderRunner.class)
public class TestWorkoutRead {

  @DataProvider
  public static Object[][] validRead() {
    WorkoutRead workout = getFullWorkoutRead();
    return new Object[][]{
            {workout}
    };
  }


  @DataProvider
  public static Object[][] invalidRead() {
    WorkoutRead emptyWorkout = new WorkoutRead();

    WorkoutRead workoutNoId = getFullWorkoutRead();
    workoutNoId.setId(null);

    WorkoutRead workoutInvalidDuration = getFullWorkoutRead();
    workoutInvalidDuration.setDuration(-10L);

    return new Object[][]{
            {emptyWorkout}, {workoutNoId}, {workoutInvalidDuration}
    };
  }


  @Test
  @UseDataProvider("validRead")
  public void testValidRead(WorkoutRead workout) {
    Assert.assertTrue(workout.canBeRead());
  }

  @Test
  @UseDataProvider("invalidRead")
  public void testInvalidRead(WorkoutRead workout) {
    Assert.assertFalse(workout.canBeRead());
  }

  private static final WorkoutRead getFullWorkoutRead() {
    WorkoutRead workout = new WorkoutRead();
    workout.setId("hello");
    workout.setDate(new Date());
    workout.setDuration(123L);
    return workout;
  }
}
