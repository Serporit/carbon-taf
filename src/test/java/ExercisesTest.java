import logging.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import screens.LoginScreen;
import screens.MainScreen;
import screens.WorkoutScreen;
import steps.CommonSteps;
import utils.Streamer;

public class ExercisesTest extends AbstractTest {
    private final LoginScreen loginScreen = new LoginScreen();
    private final MainScreen mainScreen = new MainScreen();
    private final WorkoutScreen workoutScreen = new WorkoutScreen();

    @BeforeClass
    public void startBaseDemoWorkout() {
        loginScreen.startApp();
        mainScreen.startBaseDemo().skipTimer();
        Streamer.initStreamer("init");
    }

    @Test(dataProvider = "baseDemoExercises")
    public void baseDemoExercisesTest(String exercise, int times) {
        int extra = CommonSteps.makeExerciseAndCountExtra(exercise, times);
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
    public Object[][] baseDemoExercises() {
        return new Object[][]{
                {"air_squat", 5},
//                {"dumbbell_biceps", 10},
//                {"kettlebell_swing", 10},
//                {"dumbbell_push_press", 10}
        };
    }
}
