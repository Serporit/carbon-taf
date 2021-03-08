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
        new LoginScreen().openApp();
        mainScreen.startDemo().skipTimer();
        Streamer.initStreamer("detect");
        Streamer.addVideoToQueue("push");
        String[] program = {"squat:5", "biceps:10", "kettle:10", "push:10"};
        for (String task : program) {
            String exercise = task.split(":")[0];
            int goal = Integer.parseInt(task.split(":")[1]);

            Logger.info("Starting exercise: " + exercise);
            Streamer.addVideoToQueue(exercise, goal);
            workoutScreen.waitForCounterValue(goal, goal * 3);

            int extra = 0;
            while (workoutScreen.getCount() < goal && extra < 5){
                Logger.info("Making extra exercise " + exercise);
                Streamer.addVideoToQueue(exercise);
                workoutScreen.waitForCounterValue(goal, 3);
                extra ++;
            }

            Logger.info("Resting");
            workoutScreen.rest();
        }
        Logger.info("Score: " + new ScoreScreen().getScore());
    }
}
