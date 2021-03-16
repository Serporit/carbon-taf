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

public class NewExercisesTest extends AbstractTest {
    private final LoginScreen loginScreen = new LoginScreen();
    private final MainScreen mainScreen = new MainScreen();
    private final WorkoutScreen workoutScreen = new WorkoutScreen();

    @BeforeClass
    public void startNewDemoWorkout() {
        loginScreen.startApp();
        mainScreen.startNewDemo().skipTimer();
        Streamer.initStreamer("init");
    }

    @Test(dataProvider = "newDemoExercises")
    public void baseDemoExerciseTest(String exercise, int times) {
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
