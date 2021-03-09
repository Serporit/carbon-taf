import logging.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.LoginScreen;
import screens.MainScreen;
import screens.WorkoutScreen;
import utils.Streamer;

public class StartTest extends AbsctractTest {
    public static final int MAXIMUM_EXTRA_EXERCISES = 5;
    public static final int EXERCISE_DURATION = 3;
    private final LoginScreen loginScreen = new LoginScreen();
    private final MainScreen mainScreen = new MainScreen();
    private final WorkoutScreen workoutScreen = new WorkoutScreen();

    @BeforeClass
    public void start() {
        loginScreen.openApp();
        mainScreen.startDemo().skipTimer();
        Streamer.initStreamer("detect2");
    }

    @Test(dataProvider = "exercises")
    public void exerciseTest(String exercise, int times) {
        Logger.info("Starting exercise: " + exercise);
        Streamer.addVideoToQueue(exercise, times);
        workoutScreen.waitForCounterValue(times, times * EXERCISE_DURATION);

        int extra = 0;
        while (workoutScreen.getCount() < times && extra < MAXIMUM_EXTRA_EXERCISES) {
            Logger.info("Making extra exercise " + exercise);
            Streamer.addVideoToQueue(exercise);
            workoutScreen.waitForCounterValue(times, EXERCISE_DURATION);
            extra++;
        }

        if (extra == 0) {
            Logger.info("Resting");
            workoutScreen.waitRest();
        }
        Assert.assertEquals(extra, 0, extra + " extra exercises were made.");
    }

    @DataProvider
    public Object[][] exercises() {
        return new Object[][]{
                {"squat", 5},
                {"biceps", 10},
                {"kettle", 10},
                {"push", 10},
        };
    }

//    @Test
//    public void fullDemoTestIncremental() {
//        for (int i = 0; i < 4; i++) {
//            int times = workoutScreen.getGoal();
//            String exercise = workoutScreen.getExercise();
//            Logger.info("Starting exercise: " + exercise);
//            Streamer.addVideoToQueue(exercise, times);
//            workoutScreen.waitForCounterValue(times, times * EXERCISE_DURATION);
//
//            int extra = 0;
//            while (workoutScreen.getCount() < times && extra < MAXIMUM_EXTRA_EXERCISES) {
//                Logger.info("Making extra exercise " + exercise);
//                Streamer.addVideoToQueue(exercise);
//                workoutScreen.waitForCounterValue(times, EXERCISE_DURATION);
//                extra++;
//            }
//
//            if (extra == 0) {
//                Logger.info("Resting");
//                workoutScreen.waitRest();
//            }
//        }
//        Logger.info("Score: " + new ScoreScreen().getScore());
//    }

}
