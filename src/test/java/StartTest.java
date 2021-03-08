import logging.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.*;
import utils.Streamer;

public class StartTest extends AbsctractTest {
    private MainScreen mainScreen = new MainScreen();
    private WorkoutScreen workoutScreen = new WorkoutScreen();

    @BeforeClass
    public void start() {
        new LoginScreen().openApp();
        mainScreen.startDemo().skipTimer();
        Streamer.initStreamer("detect");
        Streamer.addVideoToQueue("biceps");
    }

    @Test(dataProvider = "exercises")
    public void exerciseTest(String exercise, int times) {
        Logger.info("Starting exercise: " + exercise);
        Streamer.addVideoToQueue(exercise, times);
        workoutScreen.waitForCounterValue(times, times * 3);

        int extra = 0;
        while (workoutScreen.getCount() < times && extra < 5) {
            Logger.info("Making extra exercise " + exercise);
            Streamer.addVideoToQueue(exercise);
            workoutScreen.waitForCounterValue(times, 3);
            extra++;
        }

        Logger.info("Resting");
        workoutScreen.rest();

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
