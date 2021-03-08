import logging.Logger;
import org.testng.annotations.Test;
import screens.*;
import utils.Streamer;

public class StartTest extends AbsctractTest {
    private MainScreen mainScreen = new MainScreen();
    private PermissionScreen permissionForm = new PermissionScreen();
    private LoginScreen loginScreen = new LoginScreen();
    private WorkoutScreen workoutScreen = new WorkoutScreen();

    @Test
    public void fullDemoTest() {
        mainScreen.startDemo().skipTimer();
        Streamer.initStreamer("detect");
        Streamer.addVideoToQueue("push");

        for (int i = 0; i < 4; i++) {
            String exercise = workoutScreen.getExercise();
            Logger.info("Starting exercise: " + exercise);
            int goal = workoutScreen.getGoal();
            Streamer.addVideoToQueue(exercise, goal);
            workoutScreen.waitForCounterValue(goal, goal * 3);

            Logger.info("Resting");
            Streamer.addVideoToQueue("rest");
            workoutScreen.rest();
        }
        Logger.info("Score: " + new ScoreScreen().getScore());
    }

    @Test
    public void fullDemoTestIncremental() {
        mainScreen.startDemo().skipTimer();
        int exercisesDone, goal;
        workoutScreen.getGoal();
        Streamer.initStreamer("detect");
        Streamer.addVideoToQueue("push");
        for (int i = 0; i < 4; i++) {
            String exercise = workoutScreen.getExercise();
            Logger.info("Starting exercise: " + exercise);
            exercisesDone = 0;
            goal = workoutScreen.getGoal();
            while (workoutScreen.isCountPresent() && workoutScreen.getCount() < goal && exercisesDone < goal * 2) {
                Streamer.addVideoToQueue(exercise);
                exercisesDone++;
                workoutScreen.waitForCounterValue(exercisesDone, 3);
            }
            Logger.info(String.format("Exercises done: %s, Needed: %s", exercisesDone, goal));

            Logger.info("Resting");
            Streamer.addVideoToQueue("rest");
            workoutScreen.rest();
        }
        Logger.info("Score: " + new ScoreScreen().getScore());
    }

}
