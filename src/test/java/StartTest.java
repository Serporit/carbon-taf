import logging.Logger;
import org.testng.annotations.Test;
import screens.*;
import utils.Streamer;
import appium.Bot;

public class StartTest extends AbsctractTest{
    private MainScreen mainScreen = new MainScreen();
    private PermissionScreen permissionForm = new PermissionScreen();
    private LoginScreen loginScreen = new LoginScreen();
    private WorkoutScreen workoutScreen = new WorkoutScreen();



    @Test
    public void startWorkoutTestFirstLaunch() {
        Bot.initWithReset();
        loginScreen.openApp().signIn();
        permissionForm.allowAll();
        mainScreen.startTraining();
        permissionForm.allow().allow();
    }

    @Test
    public void startWorkoutTest() {
        loginScreen.openApp();
        mainScreen.startTraining();
    }

    @Test
    public void startDemoTest() {
        loginScreen.openApp();
        mainScreen.startDemo().skipTimer();
        Streamer.addVideoToQueue("rest");
        Streamer.addVideoToQueue("detect");
        Streamer.addVideoToQueue("squat", 5);
    }

    @Test
    public void fullDemoTest() {
        loginScreen.openApp();
        mainScreen.startDemo().skipTimer();
        int exercisesDone, goal;
        workoutScreen.getGoal();
        Streamer.initStreamer("push");
        String[] exercises = {"squat", "biceps", "kettle", "push"};
        for (String exercise : exercises) {
            Logger.info("Starting exercise: " + exercise);
            exercisesDone = 0;
            goal = workoutScreen.getGoal();
            while (workoutScreen.isCountPresent() && workoutScreen.getCount() < goal) {
                Streamer.addVideoToQueue(exercise);
                exercisesDone++;
            }
            Logger.info(String.format("Exercises done: %s, Needed: %s", exercisesDone, goal));
            Logger.info("Resting");
            Streamer.addVideoToQueue("rest");
        }
        Logger.info("Score: " + new ScoreScreen().getScore());
    }

}
