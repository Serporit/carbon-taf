import logging.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.LoginScreen;
import screens.MainScreen;
import screens.WorkoutScreen;
import steps.CommonSteps;
import utils.Streamer;

public class AllExercisesTest extends AbstractTest {
    private final LoginScreen loginScreen = new LoginScreen();
    private final MainScreen mainScreen = new MainScreen();
    private final WorkoutScreen workoutScreen = new WorkoutScreen();

    @BeforeClass
    public void startAllDemoWorkout() {
        loginScreen.startApp();
        mainScreen.startAllDemo().skipTimer();
        Streamer.initStreamer("init");
    }

    @Test(dataProvider = "allDemoExercises")
    public void allDemoExercisesTest(String exercise, int times) {
        int extra = CommonSteps.repeatExerciseAndCountExtra(exercise, times);
        Assert.assertEquals(extra, 0, extra + " extra exercises were made.");
    }

    @AfterMethod
    public void afterExercise() {
        if (workoutScreen.isExerciseComplete()) {
            Logger.info("Resting");
            workoutScreen.waitRest();
        }
    }

    @DataProvider
    public Object[][] allDemoExercises() {
        return new Object[][]{
                {"air_squat", 5},
        };
    }
}
