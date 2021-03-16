package steps;

import logging.Logger;
import screens.WorkoutScreen;
import utils.Streamer;

public class CommonSteps {
    public static final int MAXIMUM_EXTRA_EXERCISES = 5;
    public static final int SAMPLE_DURATION = 3;
    private static final WorkoutScreen workoutScreen = new WorkoutScreen();

    public static int repeatExerciseAndCountExtra(String exercise, int times) {
        Logger.info("Starting exercise: " + exercise);
        Streamer.addVideoToQueue("before_" + exercise);
        Streamer.addVideoToQueue(exercise, times);
        workoutScreen.waitForCounterValue(times, times * SAMPLE_DURATION + SAMPLE_DURATION);

        int extra = 0;
        while (workoutScreen.getCount() < times && extra < MAXIMUM_EXTRA_EXERCISES) {
            Logger.info("Making extra exercise " + exercise);
            Streamer.addVideoToQueue(exercise);
            workoutScreen.waitForCounterValue(times, SAMPLE_DURATION);
            extra++;
        }
        return extra;
    }
}
