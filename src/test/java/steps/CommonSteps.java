package steps;

import logging.Logger;
import screens.WorkoutScreen;
import utils.Streamer;

public class CommonSteps {
    public static final int MAXIMUM_EXTRA_EXERCISES = 5;
    public static final int EXERCISE_TIMEOUT = 3;
    private static final WorkoutScreen workoutScreen = new WorkoutScreen();

    public static int makeExerciseAndCountExtra(String exercise, int times) {
        Logger.info("Starting exercise: " + exercise);
        Streamer.addVideoToQueue("before_" + exercise);
        Streamer.addVideoToQueue(exercise, times);
        workoutScreen.waitForCounterValue(times, times * EXERCISE_TIMEOUT);

        int extra = 0;
        while (!workoutScreen.isExerciseComplete() && extra < MAXIMUM_EXTRA_EXERCISES) {
            Logger.info("Making extra exercise " + exercise);
            Streamer.addVideoToQueue(exercise);
            workoutScreen.waitForCounterValue(times, EXERCISE_TIMEOUT);
            extra++;
        }
        return extra;
    }
}
