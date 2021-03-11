import logging.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.LoginScreen;
import screens.MainScreen;
import screens.WorkoutScreen;
import utils.Streamer;

public class ExercisesTest extends AbsctractTest {
    public static final int MAXIMUM_EXTRA_EXERCISES = 5;
    public static final int EXERCISE_TIMEOUT = 3;
    private final LoginScreen loginScreen = new LoginScreen();
    private final MainScreen mainScreen = new MainScreen();
    private final WorkoutScreen workoutScreen = new WorkoutScreen();

    @BeforeClass
    public void start() {
        loginScreen.openApp();
        mainScreen.startDemo().skipTimer();
        Streamer.initStreamer("init");
    }

    @Test(dataProvider = "exercises")
    public void exerciseTest(String exercise, int times) {
        Logger.info("Starting exercise: " + exercise);
        Streamer.addVideoToQueue("before-" + exercise);
        Streamer.addVideoToQueue(exercise, times);
        workoutScreen.waitForCounterValue(times, times * EXERCISE_TIMEOUT);

        int extra = 0;
        while (workoutScreen.getCount() < times && extra < MAXIMUM_EXTRA_EXERCISES) {
            Logger.info("Making extra exercise " + exercise);
            Streamer.addVideoToQueue(exercise);
            workoutScreen.waitForCounterValue(times, EXERCISE_TIMEOUT);
            extra++;
        }
        if (workoutScreen.isExerciseComplete()) {
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
}
