package com.fitbod.jroland.api.workout;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(DataProviderRunner.class)
public class TestWorkoutWrite {

  @DataProvider
  public static Object[][] validWrite() {
    WorkoutWrite fullWorkout = getFullWorkoutWrite();

    WorkoutWrite noIdWorkout = getFullWorkoutWrite();
    noIdWorkout.setId(null);

    return new Object[][]{
            {fullWorkout}, {noIdWorkout}
    };
  }

  @DataProvider
  public static Object[][] invalidWrite() {
    WorkoutWrite noEmailWorkout = getFullWorkoutWrite();
    noEmailWorkout.setEmail(null);

    WorkoutWrite noDateWorkout = getFullWorkoutWrite();
    noDateWorkout.setDate(null);

    WorkoutWrite invalidDurationWorkout = new WorkoutWrite();
    invalidDurationWorkout.setDuration(-1240L);

    return new Object[][]{
            {noEmailWorkout}, {noDateWorkout}, {invalidDurationWorkout}
    };
  }

  @Test
  @UseDataProvider("validWrite")
  public void testValidWrite(WorkoutWrite workout) {
    Assert.assertTrue(workout.canBeWritten());
  }

  @Test
  @UseDataProvider("invalidWrite")
  public void testInvalidWrite(WorkoutWrite workout) {
    Assert.assertFalse(workout.canBeWritten());
  }

  private static final WorkoutWrite getFullWorkoutWrite() {
    WorkoutWrite fullWorkout = new WorkoutWrite();
    fullWorkout.setEmail("validEmail@gmail.com");
    fullWorkout.setId("id123");
    fullWorkout.setDate(new Date());
    fullWorkout.setDuration(100L);
    return fullWorkout;
  }
}

