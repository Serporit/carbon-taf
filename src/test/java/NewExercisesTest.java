import logging.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.LoginScreen;
import screens.MainScreen;
import screens.WorkoutScreen;
import utils.Streamer;

public class NewExercisesTest extends AbstractTest {
    public static final int MAXIMUM_EXTRA_EXERCISES = 5;
    public static final int EXERCISE_TIMEOUT = 3;
    private final LoginScreen loginScreen = new LoginScreen();
    private final MainScreen mainScreen = new MainScreen();
    private final WorkoutScreen workoutScreen = new WorkoutScreen();

    @BeforeClass
    public void start() {
        loginScreen.startApp();
        mainScreen.startNewDemo().skipTimer();
        Streamer.initStreamer("init");
    }

    @Test(dataProvider = "newDemoExercises")
    public void baseDemoExerciseTest(String exercise, int times) {
        Logger.info("Starting exercise: " + exercise);
        Streamer.addVideoToQueue("before_" + exercise);
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
    public Object[][] newDemoExercises() {
        return new Object[][]{
                {"dumbbell_deadlift", 5},
                {"barbell_deadlift", 5},
                {"dumbbell_thruster", 5},
                {"barbell_thruster", 5},
                {"jumping_jack", 5},
                {"pull_apart", 5},
        };
    }
}
